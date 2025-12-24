package io.github.jakmodz.backend.services;

import io.github.jakmodz.backend.dtos.UserDto;

public interface UserService {
    void registerUser(UserDto user);
}
