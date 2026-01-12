package io.github.jakmodz.backend.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(columnDefinition = "TEXT")
    String title;
    @Column(columnDefinition = "TEXT")
    String content;

    @ManyToOne
    @JsonBackReference
    User user;

    @OneToOne
    Notebook notebook;

    @CreationTimestamp
    LocalDateTime created;
    @UpdateTimestamp
    LocalDateTime modified;
}
