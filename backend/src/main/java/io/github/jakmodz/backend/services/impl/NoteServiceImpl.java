package io.github.jakmodz.backend.services.impl;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.exceptions.ForbiddenAccessException;
import io.github.jakmodz.backend.exceptions.NoteNotFoundException;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.NoteRepository;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.NoteService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final Logger logger= LoggerFactory.getLogger(NoteServiceImpl.class);
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    @Override
    public Note createNote(NoteDto note, User user) {
        logger.debug("Creating note: {}", note);
        Note newNote = transformToEntity(note);
        newNote.setUser(user);
        noteRepository.save(newNote);
        return newNote;
    }

    @Override
    public void updateNoteById(UUID noteId, NoteDto noteDto, User user) {
        Note note = getNoteById(noteId, user);
        logger.debug("Updating note: {}", note);
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        noteRepository.save(note);
        logger.debug("Updated note: {}", note);
    }
    @Override
    public Note getNoteById(UUID noteId, User user) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        if (!note.getUser().getId().equals(user.getId())) {
            logger.error("User is not the owner of this note: {}",noteId);
            throw new ForbiddenAccessException("You do not have access to this note");
        }
        logger.debug("Getting note: {}", note);
        return note;
    }
    @Override
    public void deleteNoteById(UUID noteId, User user) {
        Note note = getNoteById(noteId, user);
        logger.debug("Deleting note: {}", note);
        noteRepository.delete(note);
        logger.debug("Deleted note: {}", note);
    }

    @Override
    public List<Note> getAllNotes(User user) {
        logger.debug("Getting all notes: {}", user);
        return noteRepository.findByUser(user);
    }
}
