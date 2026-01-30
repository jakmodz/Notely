package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.dtos.PaginationResult;
import io.github.jakmodz.backend.models.Note;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.security.CustomUserDetails;
import io.github.jakmodz.backend.security.RateLimit;
import io.github.jakmodz.backend.services.NoteService;
import io.github.jakmodz.backend.services.UserService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.UUID;
@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NoteService noteService;
    private final UserService userService;
    @Autowired
    public NotesController(NoteService noteService, AuthenticationManager authenticationManager, UserService userService) {
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
    @RateLimit(limit = 1, timeWindowSeconds = 5)
    public ResponseEntity<NoteDto> createNote(@AuthenticationPrincipal CustomUserDetails customUser,@Valid @RequestBody NoteDto note,  @RequestParam(required = false) UUID notebookId) {
        User user = customUser.getUserEntity();
        NoteDto noteDto =noteService.transformToDto(noteService.createNote(note,user,notebookId));
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
            @AuthenticationPrincipal CustomUserDetails customUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "") String searchValue
    ) {

        User user = customUser.getUserEntity();

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));


        PaginationResult<NoteDto> result = noteService.getAllNotesPaginated(user, pageable, searchValue);

        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNote(@AuthenticationPrincipal CustomUserDetails customUser, @PathVariable UUID id) {
        User user = customUser.getUserEntity();
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
    public ResponseEntity<Void> deleteNote(@AuthenticationPrincipal CustomUserDetails customUser, @PathVariable UUID id) {
        User user = customUser.getUserEntity();
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
    @RateLimit(limit = 1, timeWindowSeconds = 10)
    public ResponseEntity<Void> updateNote(@AuthenticationPrincipal CustomUserDetails customUser, @PathVariable UUID id, @Valid @RequestBody NoteDto noteDto) {
        User user = customUser.getUserEntity();
        noteService.updateNoteById(id, noteDto, user);
        return ResponseEntity.ok().build();
    }
}