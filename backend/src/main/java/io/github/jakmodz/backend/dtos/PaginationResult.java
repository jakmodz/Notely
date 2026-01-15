package io.github.jakmodz.backend.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PaginationResult<T> {
    private List<T> items;
    private int totalPages;
    private long totalElements;
    private int size;
    private int page;
    private boolean empty;
}
