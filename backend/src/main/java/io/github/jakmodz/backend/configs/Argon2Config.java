package io.github.jakmodz.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class Argon2Config {
    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder()  {
        return new Argon2PasswordEncoder(16, 32, 5, 60000, 4);
    }
}
