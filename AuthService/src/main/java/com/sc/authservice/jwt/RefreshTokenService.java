package com.sc.authservice.jwt;

import com.sc.authservice.entities.RefreshToken;
import com.sc.authservice.entities.UserInfo;
import com.sc.authservice.repositories.RefreshTokenRepository;
import com.sc.authservice.repositories.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserInfoRepository userInfoRepository;

    public RefreshToken createRefreshToken(String username) {
        UserInfo userInfoExtract = userInfoRepository.findByUsername(username);

        RefreshToken refreshToken = RefreshToken.builder().userInfo(userInfoExtract)
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(864000))
            .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) throws RuntimeException {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(
                refreshToken.getToken() + "\nRefresh-Token is expired. Please login again.");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String jwtToken) {
        return refreshTokenRepository.findByToken(jwtToken);
    }
}