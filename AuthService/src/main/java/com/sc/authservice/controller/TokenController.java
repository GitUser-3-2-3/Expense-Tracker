package com.sc.authservice.controller;

import com.sc.authservice.request.AuthRequestDTO;
import com.sc.authservice.request.RefreshTokenRequestDTO;
import com.sc.authservice.response.JwtResponseDTO;
import com.sc.authservice.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("auth/v1/")
public class TokenController {

    private final AuthenticationService authenticationService;

    @Autowired
    public TokenController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<?> loginAndGetToken(@RequestBody AuthRequestDTO requestDTO) {
        try {
            Object jwtResponse = authenticationService.loginAndGetToken(requestDTO);
            return new ResponseEntity<>(jwtResponse, OK);
        }
        catch (Exception rtExp) {
            return new ResponseEntity<>("Exception in User service: " + rtExp.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO requestDTO) {
        try {
            JwtResponseDTO responseDTO = authenticationService.refreshToken(requestDTO);
            return new ResponseEntity<>(responseDTO, CREATED);
        }
        catch (Exception rtExp) {
            return new ResponseEntity<>("Refresh Token not found!!!", NOT_FOUND);
        }
    }
}