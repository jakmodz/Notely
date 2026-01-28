package io.github.jakmodz.backend.controllers;

import io.github.jakmodz.backend.dtos.NotebookDto;
import io.github.jakmodz.backend.dtos.NotebookTreeDto;
import io.github.jakmodz.backend.models.Notebook;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.NotebookRepository;
import io.github.jakmodz.backend.services.NotebookService;
import io.github.jakmodz.backend.services.UserService;
import io.github.jakmodz.backend.services.impl.NotebookServiceImpl;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<NotebookTreeDto>> getAllNotebooks(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(notebookService.getNotebookTreeWithNotes(user));
    }
    @PostMapping("/create")
    public ResponseEntity<NotebookTreeDto> createNotebook(Principal principal, @RequestBody NotebookDto notebookDto) {
        User user = userService.getUserByUsername(principal.getName());
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

}
