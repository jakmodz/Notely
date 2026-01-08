package io.github.jakmodz.backend.services.impl;

import io.github.jakmodz.backend.dtos.UserCredentials;
import io.github.jakmodz.backend.exceptions.UsernameAlreadyTaken;
import io.github.jakmodz.backend.models.User;
import io.github.jakmodz.backend.repositories.UserRepository;
import io.github.jakmodz.backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Argon2PasswordEncoder encoder;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
     UserServiceImpl(UserRepository userRepository, Argon2PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void registerUser(UserCredentials user) {
        logger.info("Registering user {}", user.getUsername());
        if(userRepository.existsByUsername(user.getUsername())) {
            logger.error("User {} already exists", user.getUsername());
            throw new UsernameAlreadyTaken("Username already taken");
        }
        String password = encoder.encode(user.getPassword());
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(password);

        userRepository.save(newUser);
    }

    @Override
    public void updatePassword(String password,User user) {
        logger.info("Updating user {}", user.getUsername());
        String hashedPassword = encoder.encode(password);

        if(hashedPassword.equals(user.getPassword())) {
            //TODO throw error same password
        }
        logger.info("password changed for user: {}",user.getUsername());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
