package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.dtos.PaginationResult;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.services.impl.NoteServiceImpl;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NoteServiceImpl noteService;
    private final UserServiceImpl userService;
    @Autowired
    public NotesController(NoteServiceImpl noteService, AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
    @Operation(summary = "Creating new note ",description = "Creating new note for user that sends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created note"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to create a note")
        }
    )
    @PostMapping("/create")
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto note, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        NoteDto noteDto =noteService.transformToDto(noteService.createNote(note,user));
        return ResponseEntity.ok(noteDto);
    }
    @Operation(summary = "Getting paginated notes",
            description = "Getting paginated notes for user that sends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated notes"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access these notes")
    }
    )
    @GetMapping
    public ResponseEntity<PaginationResult<NoteDto>> getAllNotesPaginated(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        PaginationResult<NoteDto> result = noteService.getAllNotesPaginated(user, pageable);

        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNote(Principal principal, @PathVariable UUID id) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        Note note = noteService.getNoteById(id, user);
        NoteDto noteDto = noteService.transformToDto(note);
        return ResponseEntity.ok(noteDto);
    }
    @Operation(summary = "Deleting single note ",description = "Deleting single note by id for user that sends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted note"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to delete this note")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(Principal principal, @PathVariable UUID id) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        noteService.deleteNoteById(id, user);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Updating  note ",description = "Updating  note by id for user that sends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated note"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to update this note")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNote(Principal principal, @PathVariable UUID id, @Valid @RequestBody NoteDto noteDto) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        noteService.updateNoteById(id, noteDto, user);
        return ResponseEntity.ok().build();
    }
}