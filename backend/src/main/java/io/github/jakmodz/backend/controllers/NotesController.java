package io.github.jakmodz.backend.controllers;


import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.services.NoteService;
import io.github.jakmodz.backend.services.impl.NoteServiceImpl;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
//TODO: swagger docs
//TODO: logging
@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NoteServiceImpl noteService;
    private final UserServiceImpl userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public NotesController(NoteServiceImpl noteService, AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createNote(@Valid @RequestBody NoteDto note, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        noteService.createNote(note,user);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes(Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        List<Note> notes = noteService.getAllNotes(user);
        List<NoteDto> noteDtos = notes.stream().map(noteService::transformToDto).toList();
        return ResponseEntity.ok(noteDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNote(Principal principal, @PathVariable UUID id) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Note note = noteService.getNoteById(id, user);
        NoteDto noteDto = noteService.transformToDto(note);
        return ResponseEntity.ok(noteDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(Principal principal, @PathVariable UUID id) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        noteService.deleteNoteById(id, user);
        return ResponseEntity.ok().build();
    }
}