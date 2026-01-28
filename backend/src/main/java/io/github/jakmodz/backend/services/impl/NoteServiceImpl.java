package io.github.jakmodz.backend.services.impl;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.dtos.PaginationResult;
import io.github.jakmodz.backend.exceptions.ForbiddenAccessException;
import io.github.jakmodz.backend.exceptions.NoteNotFoundException;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.NoteRepository;
import io.github.jakmodz.backend.services.NoteService;
import io.github.jakmodz.backend.services.NotebookService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NotebookService notebookService;
    private final Logger logger= LoggerFactory.getLogger(NoteServiceImpl.class);
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NotebookServiceImpl notebookService) {
        this.noteRepository = noteRepository;
        this.notebookService = notebookService;
    }

    @Transactional
    @Override
    @CacheEvict(value = "notes", allEntries = true)
    public Note createNote(NoteDto note, User user,UUID notebookId) {
        logger.debug("Creating note: {}", note);
        Note newNote = transformToEntity(note);
        newNote.setUser(user);
        if (notebookId != null) {
            newNote.setNotebook(notebookService.getByIdAndUser(notebookId, user));
        }
        noteRepository.save(newNote);
        return newNote;
    }
    @Override
    @Caching(
            put = @CachePut(value = "note", key = "#noteId"),
            evict = @CacheEvict(value = "notes", allEntries = true)
    )
    public Note updateNoteById(UUID noteId, NoteDto noteDto, User user) {
        Note noteById = getNoteById(noteId, user);
        logger.debug("Updating note: {}", noteById.getUuid());
        noteById.setTitle(noteDto.getTitle());
        noteById.setContent(noteDto.getContent());
        noteRepository.save(noteById);
        logger.debug("Updated note: {}", noteById.getUuid());
        return noteById;
    }
    @Override
    @Cacheable(value = "note", key = "#noteId")
    public Note getNoteById(UUID noteId, User user) {
        Note noteByid = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + noteId));
        if (!noteByid.getUser().getId().equals(user.getId())) {
            logger.error("User is not the owner of this note: {}",noteId);
            throw new ForbiddenAccessException("You do not have access to this note");
        }
        logger.debug("Getting note: {}", noteByid);
        return noteByid;
    }
    @Override
    @Caching(evict = {
            @CacheEvict(value = "note", key = "#noteId"),
            @CacheEvict(value = "notes", key = "#user.id")
    })
    public void deleteNoteById(UUID noteId, User user) {
        Note note = getNoteById(noteId, user);
        logger.debug("Deleting note: {}", note);
        noteRepository.delete(note);
        logger.debug("Deleted note: {}", note);
    }


    @Override
    public PaginationResult<NoteDto> getAllNotesPaginated(User user, Pageable page,String search) {
        logger.debug("Getting paginated notes for user: {} with page: {}, size: {}",
                user.getUsername(), page.getPageNumber(), page.getPageSize());

        Page<Note> notesPage;
        if (search == null || search.trim().isEmpty()) {
             notesPage = noteRepository.findByUser(user, page);
        }else{
            notesPage = noteRepository.findByUserAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(user, search, search, page);
        }

        List<NoteDto> notes = notesPage.stream()
                .map(this::transformToDto)
                .toList();
        logger.debug("Retrieved {} notes out of {} total",
                notes.size(), notesPage.getTotalElements());

        return new PaginationResult<NoteDto>(
                notes,
                notesPage.getTotalPages(),
                notesPage.getTotalElements(),
                notesPage.getSize(),
                notesPage.getNumber(),
                notesPage.isEmpty()
        );
    }

    @Override
    public List<Note> getNoteByNotebookParentId(UUID notebookId, User user) {
        logger.debug("Getting notes for notebook id: {} and user: {}", notebookId, user.getUsername());
        List<Note> notes = noteRepository.findByNotebook(notebookId, user);
        logger.debug("Retrieved {} notes for notebook id: {}", notes.size(), notebookId);
        return notes;
    }
}
