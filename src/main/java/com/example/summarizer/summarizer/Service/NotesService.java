package com.example.summarizer.summarizer.Service;

import com.example.summarizer.summarizer.Entity.NotesEntity;
import com.example.summarizer.summarizer.Repository.NotesRepository;

import java.util.List;
import java.util.UUID;

public class NotesService {
    private final NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository){
        this.notesRepository = notesRepository;
    }

    public List<NotesEntity> findByUserId(UUID userId){
        return notesRepository.findByUserId(userId);
    }

    public NotesEntity saveNotes(NotesEntity notes){
        return notesRepository.save(notes);
    }

    public void deleteNotes(UUID notesId){
        notesRepository.deleteById(notesId);
    }
}
