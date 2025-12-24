package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.UserDto;
import io.github.jakmodz.backend.services.UserService;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
     UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto user) {
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }



}
