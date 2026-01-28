package io.github.jakmodz.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotebookTreeDto {
    private UUID id;
    private String label;
    private String type;
    private UUID parentNotebookId;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<NotebookTreeDto> children;

    private String content;
    private LocalDateTime modified;
}
