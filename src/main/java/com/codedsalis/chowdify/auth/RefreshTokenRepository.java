package com.codedsalis.chowdify.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID>{

    Optional<RefreshToken> findByToken(UUID refreshToken);

}
