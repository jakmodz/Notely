package io.github.jakmodz.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
}
