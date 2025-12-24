package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.UserDto;
import io.github.jakmodz.backend.jwt.JwtService;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.UserService;
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
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;
    @Autowired
     UserController(UserServiceImpl userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto user) {
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto user) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwt);
    }


}
