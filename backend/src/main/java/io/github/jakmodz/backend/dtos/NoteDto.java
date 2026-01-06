package io.github.jakmodz.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private UUID uuid;
    @NotBlank
    private String title;
    private String content;
    private LocalDateTime created;
}
