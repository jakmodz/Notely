package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.UserDto;
import io.github.jakmodz.backend.jwt.JwtService;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.RefreshTokenService;
import io.github.jakmodz.backend.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenDuration;
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    @Autowired
     UserController(UserServiceImpl userService, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto user) {
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto user, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails.getUsername());
        String refresh = refreshTokenService.createRefreshToken(userDetails.getUsername());
        setRefreshTokenCookie(response, refresh);
        return ResponseEntity.ok(jwt);
    }
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@CookieValue(name ="refreshToken") String refreshToken,HttpServletResponse response) {
        if (jwtService.validateJwtToken(refreshToken)) {
            String username = jwtService.getUsernameFromToken(refreshToken);
            String newJwt = jwtService.generateToken(username);
            String newRefresh = refreshTokenService.createRefreshToken(username);
            setRefreshTokenCookie(response, newRefresh);
            return ResponseEntity.ok(newJwt);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletResponse response) {
        clearRefreshTokenCookie(response);
        return ResponseEntity.ok().build();
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(Math.toIntExact(refreshTokenDuration));
        response.addCookie(cookie);
    }
    private void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
