package com.example.summarizer.summarizer.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "summaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    @JsonProperty("note_id")
    private NotesEntity note;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    private UserEntity user;

    @Column(columnDefinition = "TEXT", nullable = false)
    @JsonProperty("summaryText")
    private String summaryText;

    @Column(length = 50, nullable = false)
    @JsonProperty("modelUsed")
    private String modelUsed;

    @Column(nullable = false)
    @JsonProperty("apiCost")
    private int tokenUsed;

    @Column(nullable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();


}
