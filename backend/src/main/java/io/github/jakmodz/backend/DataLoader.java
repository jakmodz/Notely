package io.github.jakmodz.backend;

import io.github.jakmodz.backend.dtos.NoteDto;
import io.github.jakmodz.backend.dtos.UserCredentials;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.impl.NoteServiceImpl;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {
    private final String MARKDOWN ="""
    # Heading 1
    This is a paragraph with **bold text** and *italic text* and ***bold italic***.

            ## Heading 2
    Here's a [link to Google](https://google.com) and some `inline code`.

            ### Heading 3
            #### Heading 4

            ### Lists

**Unordered list:**
            - Item one
- Item two
- Nested item
- Another nested item
- Item three

**Ordered list:**
            1. First item
2. Second item
3. Third item

### Code Block

```javascript
    function hello() {
        console.log("Hello, World!");
        return true;
    }
```

        ### Blockquote

> This is a blockquote.
            > It can span multiple lines.

### Task List

- [x] Completed task
- [ ] Incomplete task
- [ ] Another task

### Table

| Feature | Status |Priority |
            |---------|--------|----------|
            | Dark Mode | ✅ | High |
            | Search | 🚧 | Medium |
            | Export | ❌ | Low |

            ### Horizontal Rule

---

        ### Emphasis

    This is **bold**, this is *italic*, and this is ~~strikethrough~~.
            **Random Photo:**
            ### Images
![Random](https://picsum.photos/600/300)

            ![Alt text](https://via.placeholder.com/150)
            ---

    That's it! If you can see all these elements properly styled, markdown is working correctly.""";
    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserServiceImpl userService;
    private final NoteServiceImpl noteService;
    private final UserRepository userRepository;
    @Autowired
    public DataLoader(UserServiceImpl userService, NoteServiceImpl noteService, UserRepository userRepository) {
        this.userService = userService;
        this.noteService = noteService;
        this.userRepository = userRepository;
    }
    private NoteDto createSampleNote(String title, String content) {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(title);
        noteDto.setContent(content);
        return noteDto;
    }
    public void run(String... args) throws Exception {
        if(userRepository.findAll().isEmpty()) {
            UserCredentials user1Creds = new UserCredentials("user1", "password1");
            UserCredentials user2Creds = new UserCredentials("user2", "password2");
            NoteDto note1 = createSampleNote("Title 1", "### Content 1");
            NoteDto note2 = createSampleNote("Title 2", "Content 2");
            NoteDto note3 = createSampleNote("Markdown Sample", MARKDOWN);

            try {
                userService.registerUser(user1Creds);
                userService.registerUser(user2Creds);
                logger.info("Default users created: {}, {}", user1Creds.getUsername(), user2Creds.getUsername());

                User user1 = userRepository.findByUsername(user1Creds.getUsername());
                User user2 = userRepository.findByUsername(user2Creds.getUsername());


                noteService.createNote(note1, user1);
                noteService.createNote(note2, user2);
                noteService.createNote(note3, user1);
                noteService.createNote(note3, user2);
                logger.info("Sample notes created for default users");
            } catch (Exception e) {
                logger.error("Error creating default users: {}", e.getMessage());
            }
        }
    }
}
