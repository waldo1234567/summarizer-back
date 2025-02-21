package com.example.summarizer.summarizer.Repository;

import com.example.summarizer.summarizer.Entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotesRepository extends JpaRepository<NotesEntity , UUID> {
    List<NotesEntity> findByUserId(UUID userId);
}
