package io.github.jakmodz.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser() {
        return ResponseEntity.ok().build();
    }
}
