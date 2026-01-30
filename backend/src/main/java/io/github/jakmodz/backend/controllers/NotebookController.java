package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.NotebookDto;
import io.github.jakmodz.backend.dtos.NotebookTreeDto;
import io.github.jakmodz.backend.exceptions.errorHandler.ErrorResponse;
import io.github.jakmodz.backend.models.Notebook;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.security.CustomUserDetails;
import io.github.jakmodz.backend.services.NotebookService;
import io.github.jakmodz.backend.services.UserService;
import io.github.jakmodz.backend.services.impl.NotebookServiceImpl;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notebooks")
public class NotebookController {
    private final NotebookService notebookService;
    private final UserService userService;

    @Autowired
    public NotebookController(NotebookServiceImpl notebookService, UserServiceImpl userService) {
        this.notebookService = notebookService;
        this.userService = userService;
    }
    @Operation(summary = "Getting all notebooks with notes as tree structure",
            description = "Getting all notebooks with notes for user that sends request as tree structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notebooks tree"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to access these notebooks",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<NotebookTreeDto>> getAllNotebooks(@AuthenticationPrincipal CustomUserDetails customUser) {
        User user = customUser.getUserEntity();
        return ResponseEntity.ok(notebookService.getNotebookTreeWithNotes(user));
    }
    @Operation(summary = "Creating new notebook",
            description = "Creating new notebook for user that sends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created notebook"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to create a notebook",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/create")
    public ResponseEntity<NotebookTreeDto> createNotebook(@AuthenticationPrincipal CustomUserDetails customUser, @RequestBody NotebookDto notebookDto) {
        User user = customUser.getUserEntity();
        Notebook savedNotebook = notebookService.createNotebook(notebookDto, user);

        NotebookTreeDto dto = new NotebookTreeDto();
        dto.setId(savedNotebook.getId());
        dto.setLabel(savedNotebook.getName());
        dto.setType("notebook");
        dto.setCreationDate(savedNotebook.getCreationDate());
        dto.setUpdateDate(savedNotebook.getUpdateDate());
        dto.setChildren(new ArrayList<>());

        return ResponseEntity.ok(dto);
    }
    @Operation(summary = "Deleting notebook",
            description = "Deleting notebook for user that sends request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted notebook"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing authentication credentials",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - You do not have permission to delete this notebook",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found - Notebook not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{notebookId}")
    public ResponseEntity<Void> deleteNotebook(@AuthenticationPrincipal CustomUserDetails customUser, @PathVariable("notebookId") UUID notebookId) {
        User user = customUser.getUserEntity();
        notebookService.deleteNotebook(notebookId, user);
        return ResponseEntity.noContent().build();
    }
}
