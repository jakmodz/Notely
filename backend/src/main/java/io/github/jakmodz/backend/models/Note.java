package io.github.jakmodz.backend.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notes", indexes = {
        @Index(name = "idx_note_uuid", columnList = "uuid")
})
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String title;
    @Column(columnDefinition = "TEXT")
    String content;

    @ManyToOne
    User user;

    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime modified;
}
