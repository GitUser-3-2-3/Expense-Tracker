package com.sc.authservice.services;

import com.sc.authservice.entities.RefreshToken;
import com.sc.authservice.jwt.JwtService;
import com.sc.authservice.jwt.RefreshTokenService;
import com.sc.authservice.model.UserInfoDTO;
import com.sc.authservice.request.AuthRequestDTO;
import com.sc.authservice.request.RefreshTokenRequestDTO;
import com.sc.authservice.response.JwtResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(
        UserDetailsServiceImpl userDetailsService, RefreshTokenService refreshTokenService, JwtService jwtService,
        AuthenticationManager authenticationManager
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public Object signup(UserInfoDTO userInfoDTO) {
        Boolean isSignedUp = userDetailsService.signupUser(userInfoDTO);
        if (Boolean.FALSE.equals(isSignedUp)) {
            return "User already exists. Try with a different username.";
        }
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO
            .getUsername());
        String jwtToken = jwtService.generateToken(userInfoDTO.getUsername());

        return JwtResponseDTO.builder().accessToken(jwtToken)
            .refreshToken(refreshToken.getRefreshToken()).build();
    }

    public Object loginAndGetToken(AuthRequestDTO requestDTO) {
        Authentication authentication = authenticationManager.authenticate(new
            UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));

        if (!authentication.isAuthenticated()) {
            return "User could not be authenticated. Try with a different username/password.";
        }
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(requestDTO.getUsername());

        return JwtResponseDTO.builder().refreshToken(refreshToken.getRefreshToken())
            .accessToken(jwtService.generateToken(requestDTO.getUsername()))
            .build();
    }

    public JwtResponseDTO refreshToken(RefreshTokenRequestDTO requestDTO) {
        return refreshTokenService.findByToken(requestDTO.getRefreshToken())
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUserInfo)
            .map(userInfo -> {
                String jwtToken = jwtService.generateToken(userInfo.getUsername());
                return JwtResponseDTO.builder().accessToken(jwtToken)
                    .refreshToken(requestDTO.getRefreshToken())
                    .build();
            }).orElseThrow(() -> new RuntimeException("Refresh Token not found!!!"));
    }
}