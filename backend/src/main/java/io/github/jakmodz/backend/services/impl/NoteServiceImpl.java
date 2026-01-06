package io.github.jakmodz.backend.services.impl;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.exceptions.ForbiddenAccessException;
import io.github.jakmodz.backend.exceptions.NoteNotFoundException;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.NoteRepository;
import io.github.jakmodz.backend.services.NoteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    @Override
    public void createNote(NoteDto note, User user) {
        Note newNote = transformToEntity(note);
        newNote.setUser(user);
        noteRepository.save(newNote);
    }

    @Override
    public Note getNoteById(UUID noteId, User user) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        if (!note.getUser().getId().equals(user.getId())) {
            throw new ForbiddenAccessException("You do not have access to this note");
        }

        return note;
    }
    @Override
    public void deleteNoteById(UUID noteId, User user) {
        Note note = getNoteById(noteId, user);
        noteRepository.delete(note);
    }

    @Override
    public List<Note> getAllNotes(User user) {
        return noteRepository.findByUser(user);
    }
}
