package com.example.summarizer.summarizer.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user")
    @JsonBackReference
    private UserEntity user;

    @Column(nullable = false, length = 225)
    @JsonProperty("title")
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @JsonProperty("content")
    private String content;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("summaries")
    private List<SummaryEntity> summaries;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();
}
