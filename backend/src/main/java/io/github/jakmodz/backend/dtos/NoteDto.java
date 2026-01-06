package io.github.jakmodz.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NoteDto {
    private UUID uuid;
    private String title;
    private String content;
    private LocalDateTime updated;
}
