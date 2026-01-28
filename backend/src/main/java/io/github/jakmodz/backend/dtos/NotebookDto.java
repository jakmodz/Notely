package io.github.jakmodz.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotebookDto {
    private UUID id;
    @NotBlank
    private String name;
    private UUID parentNotebookId;
}
