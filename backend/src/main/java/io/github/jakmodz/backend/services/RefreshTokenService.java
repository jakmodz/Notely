package io.github.jakmodz.backend.services;

import io.github.jakmodz.backend.models.RefreshToken;
import io.github.jakmodz.backend.models.User;

import java.util.Optional;

public interface RefreshTokenService {
    String createRefreshToken(String username);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    void deleteByUser(User user);
    void deleteByUserId(Long userId);
}
