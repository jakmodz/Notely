package io.github.jakmodz.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    @NotBlank(message= "Username cannot be blank")
    private String username;
    @NotBlank(message= "Password cannot be blank")
    private String password;
}