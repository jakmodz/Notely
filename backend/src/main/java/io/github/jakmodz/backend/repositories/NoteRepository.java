package io.github.jakmodz.backend.repositories;

import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.Notebook;
import io.github.jakmodz.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByUser(User user);
    Page<Note> findByUser(User user, Pageable pageable);

    List<Note> findByNotebookAndUser(Notebook notebook, User user);
    Page<Note> findByUserAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            User user, String title, String content, Pageable pageable);

    List<Note> findByNotebook(UUID notebookId, User user);
}
