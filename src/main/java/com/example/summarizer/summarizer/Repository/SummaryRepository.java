package com.example.summarizer.summarizer.Repository;

import com.example.summarizer.summarizer.Entity.SummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SummaryRepository extends JpaRepository<SummaryEntity , UUID> {
    List<SummaryEntity> findByUserId (UUID userId);
}
