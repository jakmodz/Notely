package io.github.jakmodz.backend.services.impl;

import io.github.jakmodz.backend.jwt.JwtService;
import io.github.jakmodz.backend.models.RefreshToken;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.RefreshTokenRepository;
import io.github.jakmodz.backend.services.RefreshTokenService;
import io.github.jakmodz.backend.services.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenRepository repository;
    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenDuration;

    private final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    @Autowired
    public RefreshTokenServiceImpl(JwtService jwtService, UserService userService, RefreshTokenRepository repository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.repository = repository;
    }

    @Override
    public String createRefreshToken(String username) {

        User user = userService.getUserByUsername(username);
        repository.findByUser(user).ifPresent(repository::delete);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(jwtService.generateRefreshToken(username));
        repository.save(refreshToken);
        logger.info("Created refresh token: {}", refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public Optional<RefreshToken> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public void deleteByUser(User user) {
        repository.deleteByUser(user);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userService.getUserById(userId);
        repository.deleteByUser(user);
        logger.info("User {} has been deleted", userId);
    }
}
