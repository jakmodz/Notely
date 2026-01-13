package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.*;
import io.github.jakmodz.backend.exceptions.errorHandler.ErrorResponse;
import io.github.jakmodz.backend.exceptions.ExpiredRefreshToken;
import io.github.jakmodz.backend.jwt.JwtService;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.security.RateLimit;
import io.github.jakmodz.backend.services.RefreshTokenService;
import io.github.jakmodz.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenDuration;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
     UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }
    @Operation(summary = "Register new user", description = "Registers a new user with username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Username already taken",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    @RateLimit(limit = 1, timeWindowSeconds = 300)
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserCredentials user) {
        userService.registerUser(user);
        logger.info("Registered new user: {}", user.getUsername());
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Login user", description = "Authenticates user and returns access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                content = @Content(schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or expired token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserCredentials user, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails.getUsername());
        String refresh = refreshTokenService.createRefreshToken(userDetails.getUsername());
        setRefreshTokenCookie(response, refresh);
        return ResponseEntity.ok(new LoginResponse(jwt, new UserDto(userDetails.getUsername())));
    }
    @Operation(summary = "Refresh access token", description = "Generates a new access token using the refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated new access token",
            content = @Content(schema = @Schema(implementation = AccessToken.class))),
            @ApiResponse(responseCode = "401", description = "Expired or invalid refresh token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<AccessToken> refreshToken(@CookieValue(name ="refreshToken") String refreshToken,HttpServletResponse response) {
        if (jwtService.validateJwtToken(refreshToken)) {
            String username = jwtService.getUsernameFromToken(refreshToken);
            String newJwt = jwtService.generateToken(username);
            String newRefresh = refreshTokenService.createRefreshToken(username);
            setRefreshTokenCookie(response, newRefresh);
            return ResponseEntity.ok(new AccessToken(newJwt));
        } else {
            throw new ExpiredRefreshToken("Refresh token is expired or invalid");
        }
    }
    @Operation(summary = "Logout user", description = "Logs out the user by clearing the refresh token cookie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletResponse response) {
        clearRefreshTokenCookie(response);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Reset user password", description = "Resets the password for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reset password"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody NewPassword newPassword, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        userService.updatePassword(newPassword.getPassword(),user);
        return ResponseEntity.ok().build();
    }
    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(Math.toIntExact(refreshTokenDuration));
        response.addCookie(cookie);
    }
    private void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
