package io.github.jakmodz.backend;

import io.github.jakmodz.backend.dtos.UserCredentials;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    @Autowired
    public DataLoader(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findAll().isEmpty()) {
            UserCredentials user = new UserCredentials("user1", "password1");
            UserCredentials user2 = new UserCredentials("user2", "password2");
            try {
                userService.registerUser(user);
                userService.registerUser(user2);
                logger.info("Default users created: {}, {}", user.getUsername(), user2.getUsername());
            } catch (Exception e) {
                logger.error("Error creating default users: {}", e.getMessage());
            }
        }
    }
}
