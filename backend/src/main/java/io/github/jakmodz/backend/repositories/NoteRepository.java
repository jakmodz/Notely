package io.github.jakmodz.backend.repositories;

import io.github.jakmodz.backend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

interface NoteRepository extends JpaRepository<Note, UUID> {

}
