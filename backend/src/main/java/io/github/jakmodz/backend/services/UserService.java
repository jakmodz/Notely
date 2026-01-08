package io.github.jakmodz.backend.services;

import io.github.jakmodz.backend.dtos.UserCredentials;
import io.github.jakmodz.backend.models.User;

public interface UserService {
    void registerUser(UserCredentials user);
    User getUserByUsername(String username);
    User getUserById(Long id);
    void updatePassword(String password,User user);
}
