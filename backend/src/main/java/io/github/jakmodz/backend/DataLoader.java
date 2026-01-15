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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserServiceImpl userService;
    private final NoteServiceImpl noteService;
    private final UserRepository userRepository;
    private final Random random = new Random();

    private static final String MARKDOWN = """
            # Sample Markdown Note

            This is a sample note written in **Markdown** format.

            ## Features

            - **Bold Text**
            - *Italic Text*
            - `Inline Code`
            - [Links](https://example.com)
            - Images

            ![Sample Image](https://via.placeholder.com/150)

            ## Code Block

            ```python
            def hello_world():
                print("Hello, World!")
            ```

            ## Lists

            1. First item
            2. Second item
               - Subitem A
               - Subitem B
            3. Third item

            > This is a blockquote.

            Enjoy using Markdown for your notes!
            """;
    private final String[] noteTitles = {
            "Meeting Notes", "Project Ideas", "Shopping List", "Daily Journal",
            "Code Snippets", "Book Notes", "Travel Plans", "Recipe Collection",
            "Workout Routine", "Budget Planning", "Learning Resources", "Goals for 2024",
            "Client Meeting", "Bug Report", "Feature Request", "Design Ideas",
            "Research Notes", "Interview Questions", "Team Updates", "Personal Reflections"
    };

    private final String[] noteContents = {
            "# Important Points\n- First point\n- Second point\n- Third point",
            "## Task List\n- [ ] Task one\n- [x] Task two\n- [ ] Task three",
            "```java\npublic void example() {\n    System.out.println(\"Hello\");\n}\n```",
            "This is a simple note with some **bold text** and *italic text*.",
            "> Remember to review this later\n\nSome additional notes here.",
            "### Weekly Goals\n1. Complete project\n2. Review code\n3. Write documentation",
            MARKDOWN,
            "## Quick Thoughts\nJust jotting down some ideas for later review.",
            "### Important Links\n- [Google](https://google.com)\n- [GitHub](https://github.com)",
            "**Priority:** High\n\nNeed to follow up on this ASAP."
    };

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

    private void createManyUsersAndNotes() {
        logger.info("========================================");
        logger.info("POPULATING DATABASE WITH TEST DATA");
        logger.info("========================================");

        List<User> users = new ArrayList<>();
        int userCount = 10;
        int notesPerUser = 2000;

        logger.info("Creating {} users...", userCount);
        for (int i = 1; i <= userCount; i++) {
            try {
                UserCredentials credentials = new UserCredentials("user" + i, "password" + i);
                userService.registerUser(credentials);
                User user = userRepository.findByUsername(credentials.getUsername());
                users.add(user);
            } catch (Exception e) {
                logger.error("Error creating user {}: {}", i, e.getMessage());
            }
        }
        logger.info("✓ Created {} users", users.size());

        logger.info("Creating {} notes per user...", notesPerUser);
        int totalNotes = 0;
        for (User user : users) {
            for (int i = 0; i < notesPerUser; i++) {
                try {
                    String title = noteTitles[random.nextInt(noteTitles.length)] + " #" + (i + 1);
                    String content = noteContents[random.nextInt(noteContents.length)];
                    NoteDto noteDto = createSampleNote(title, content);
                    noteService.createNote(noteDto, user);
                    totalNotes++;
                } catch (Exception e) {
                    logger.error("Error creating note for user {}: {}", user.getUsername(), e.getMessage());
                }
            }
        }
        logger.info("✓ Created {} total notes", totalNotes);
        logger.info("========================================");
    }



    public void run( String... args) {
        if(userRepository.findAll().isEmpty()) {
            createManyUsersAndNotes();
        } else {
            logger.info("Database already contains data, skipping initialization");
        };
    }

}
