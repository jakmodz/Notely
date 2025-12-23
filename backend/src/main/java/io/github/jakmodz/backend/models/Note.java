package io.github.jakmodz.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "notes", indexes = {
        @Index(name = "idx_note_uuid", columnList = "uuid")
})
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String title;
    String content;

    @OneToMany
    User userId;

    LocalDateTime created;
    LocalDateTime modified;
}
