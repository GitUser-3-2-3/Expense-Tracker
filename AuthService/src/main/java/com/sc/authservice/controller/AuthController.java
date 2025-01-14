package com.sc.authservice.controller;

import com.sc.authservice.entities.RefreshToken;
import com.sc.authservice.jwt.JwtService;
import com.sc.authservice.jwt.RefreshTokenService;
import com.sc.authservice.model.UserInfoDTO;
import com.sc.authservice.response.JwtResponseDTO;
import com.sc.authservice.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("auth/v1/")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(
        UserDetailsServiceImpl userDetailsService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isSignedUp = userDetailsService.signupUser(userInfoDTO);
            if (Boolean.FALSE.equals(isSignedUp)) {
                return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO
                .getUsername());
            String jwtToken = jwtService.createToken(new HashMap<>(), userInfoDTO.getUsername());

            JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder().accessToken(jwtToken)
                .refreshToken(refreshToken.getToken()).build();

            return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
        }
        catch (Exception rtExp) {
            return new ResponseEntity<>("Exception in user service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}