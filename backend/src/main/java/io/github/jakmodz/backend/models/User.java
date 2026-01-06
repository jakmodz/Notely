package io.github.jakmodz.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users", indexes = {
        @Index(name = "idx_user_id", columnList = "id"),
        @Index(name = "idx_user_username", columnList = "username")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @Column(unique = true)
    String username;
    String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<Note> notes;


    @CreationTimestamp
    LocalDateTime created;
    @LastModifiedDate
    LocalDateTime modified;
}