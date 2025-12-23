package io.github.jakmodz.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class Agron2Config {
    @Bean
    Argon2PasswordEncoder argon2PasswordEncoder()  {
        return new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
    }
}
