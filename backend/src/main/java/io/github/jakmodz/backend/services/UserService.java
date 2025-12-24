package io.github.jakmodz.backend.services;

import io.github.jakmodz.backend.dtos.UserDto;
import io.github.jakmodz.backend.models.User;

public interface UserService {
    void registerUser(UserDto user);
    User getUserByUsername(String username);
    User getUserById(Long id);
}
