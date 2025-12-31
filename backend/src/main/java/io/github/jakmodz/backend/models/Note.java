package io.github.jakmodz.backend.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

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

    @ManyToOne
    User userId;

    LocalDateTime created;
    LocalDateTime modified;
}
