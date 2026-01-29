package io.github.jakmodz.backend.services.impl;

import io.github.jakmodz.backend.dtos.NotebookDto;
import io.github.jakmodz.backend.dtos.NotebookTreeDto;
import io.github.jakmodz.backend.exceptions.ForbiddenAccessException;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.Notebook;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.NoteRepository;
import io.github.jakmodz.backend.repositories.NotebookRepository;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.NoteService;
import io.github.jakmodz.backend.services.NotebookService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotebookServiceImpl implements NotebookService {
    private final NotebookRepository notebookRepository;
    private final NoteRepository noteRepository;

    private final Logger logger = LoggerFactory.getLogger(NotebookServiceImpl.class);
    //TODO: refactor circualar dependency between NoteService and NotebookService
    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepository,NoteRepository noteRepository) {
        this.notebookRepository = notebookRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Notebook> getParentsNotebooks(User user) {
        return notebookRepository.findByUserAndParentNotebookIsNull(user);
    }

    @Override
    public Notebook createNotebook(NotebookDto notebookDto, User user) {
        Notebook notebook = new Notebook();
        notebook.setName(notebookDto.getName());
        if (notebookDto.getParentNotebookId() != null) {
            Notebook parentNotebook = getByIdAndUser(notebookDto.getParentNotebookId(), user);
            notebook.setParentNotebook(parentNotebook);
        }
        notebook.setUser(user);
        logger.debug("Creating notebook: {}", notebookDto.getName());
        return notebookRepository.save(notebook);
    }

    @Override
    public Notebook getNotebookById(UUID notebookId) {
        return notebookRepository.findById(notebookId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Notebook not found")
                );
    }

    @Override
    public List<Notebook> getChildrenNotebooks(User user, Notebook notebook) {
        return notebookRepository.findByUserAndParentNotebook(user, notebook);
    }

    @Override
    public Notebook getByIdAndUser(UUID notebookId, User user) {
        return notebookRepository.findByIdAndUser(notebookId, user)
                .orElse(null);
    }

    @Override
    public List<NotebookTreeDto> getNotebookTreeWithNotes(User user) {
        List<Notebook> parentNotebooks = notebookRepository.findByUserAndParentNotebookIsNull(user);
        return parentNotebooks.stream()
                .map(notebook -> buildNotebookTree(notebook, user))
                .collect(Collectors.toList());
    }

    private NotebookTreeDto buildNotebookTree(Notebook notebook, User user) {
        NotebookTreeDto dto = new NotebookTreeDto();
        dto.setId(notebook.getId());
        dto.setLabel(notebook.getName());
        dto.setType("notebook");
        dto.setCreationDate(notebook.getCreationDate());
        dto.setUpdateDate(notebook.getUpdateDate());

        List<NotebookTreeDto> children = new ArrayList<>();

        List<Notebook> childNotebooks = notebookRepository.findByUserAndParentNotebook(user, notebook);
        for (Notebook child : childNotebooks) {
            children.add(buildNotebookTree(child, user));
        }

        List<Note> notes = noteRepository.findByNotebookAndUser(notebook, user);
        for (Note note : notes) {
            NotebookTreeDto noteDto = new NotebookTreeDto();
            noteDto.setId(note.getUuid());
            noteDto.setLabel(note.getTitle());
            noteDto.setType("note");
            noteDto.setCreationDate(note.getCreated());
            noteDto.setModified(note.getModified());
            noteDto.setChildren(null);
            children.add(noteDto);
        }

        dto.setChildren(children.isEmpty() ? null : children);
        return dto;
    }
    @Override
    @Transactional
    public void deleteNotebook(UUID notebookId, User user) {
        Notebook notebook = notebookRepository.findByIdAndUser(notebookId, user)
                .orElseThrow(() -> new ForbiddenAccessException("Notebook not found or access denied"));

        logger.debug("Deleting notebook: {} for user: {}", notebookId, user.getUsername());

        List<Notebook> childNotebooks = notebookRepository.findByUserAndParentNotebook(user, notebook);
        for (Notebook child : childNotebooks) {
            deleteNotebook(child.getId(), user);
        }

        List<Note> notes = noteRepository.findByNotebookAndUser(notebook, user);
        if (!notes.isEmpty()) {
            noteRepository.deleteAll(notes);
            logger.debug("Deleted {} notes for notebook: {}", notes.size(), notebookId);
        }

        notebookRepository.delete(notebook);
    }
}
