package io.github.jakmodz.backend.repositories;

import io.github.jakmodz.backend.models.Notebook;
import io.github.jakmodz.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotebookRepository extends JpaRepository<Notebook, UUID> {
    List<Notebook> findByUserAndParentNotebookIsNull(User user);
    List<Notebook> findByUserAndParentNotebook(User user, Notebook parentNotebook);
    int countByUser(User user);
    Optional<Notebook> findByIdAndUser(UUID notebookId, User user);
}
