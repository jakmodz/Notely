package io.github.jakmodz.backend.security;

import io.github.jakmodz.backend.models.User;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final User userEntity;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userEntity = user;
    }

    public User getUserEntity() {
        return userEntity;
    }
}