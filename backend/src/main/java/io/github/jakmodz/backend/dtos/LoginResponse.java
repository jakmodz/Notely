package io.github.jakmodz.backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String accessToken;
    UserDto user;
}
