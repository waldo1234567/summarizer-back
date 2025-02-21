package com.example.summarizer.summarizer.Service;

import com.example.summarizer.summarizer.Entity.SummaryEntity;
import com.example.summarizer.summarizer.Repository.SummaryRepository;

import java.util.List;
import java.util.UUID;

public class SummaryService {
    private final SummaryRepository summaryRepository;

    public SummaryService(SummaryRepository summaryRepository){
        this.summaryRepository = summaryRepository;
    }

    public List<SummaryEntity> findSummaryById(UUID userId){
        return summaryRepository.findByUserId(userId);
    }

    public SummaryEntity saveSummary(SummaryEntity summary){
        return summaryRepository.save(summary);
    }

}
