package io.github.jakmodz.backend.services;

import io.github.jakmodz.backend.dtos.NotebookDto;
import io.github.jakmodz.backend.dtos.NotebookTreeDto;
import io.github.jakmodz.backend.models.Notebook;
import io.github.jakmodz.backend.models.User;

import java.util.List;
import java.util.UUID;

public interface NotebookService {
    Notebook createNotebook(NotebookDto notebook, User user);
    List<Notebook> getParentsNotebooks(User user);
    Notebook getNotebookById(UUID notebookId);
    List<Notebook> getChildrenNotebooks(User user, Notebook notebook);
    Notebook getByIdAndUser(UUID notebookId, User user);
    List<NotebookTreeDto> getNotebookTreeWithNotes(User user);
}
