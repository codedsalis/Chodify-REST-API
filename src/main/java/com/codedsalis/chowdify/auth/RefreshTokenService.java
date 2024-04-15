package com.codedsalis.chowdify.auth;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codedsalis.chowdify.user.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class RefreshTokenService {

    
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenService(
        RefreshTokenRepository refreshTokenRepository,
        UserRepository userRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken creaRefreshToken(String userEmail) {
        RefreshToken refreshToken = RefreshToken.builder()
            .user(userRepository.findByEmail(userEmail))
            .token(UUID.randomUUID())
            .expirationTime(Instant.now().plusSeconds(60*60*24*3))
            .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(UUID refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
        return token;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);

            throw new ExpiredJwtException(null, null, "Expired token, please login again");
        }

        return token;
    }
}
