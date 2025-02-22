package com.example.summarizer.summarizer.Service;

import com.example.summarizer.summarizer.Entity.NotesEntity;
import com.example.summarizer.summarizer.Entity.SummaryEntity;
import com.example.summarizer.summarizer.Entity.UserEntity;
import com.example.summarizer.summarizer.Repository.NotesRepository;
import com.example.summarizer.summarizer.Repository.SummaryRepository;
import com.example.summarizer.summarizer.Repository.UserRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotesService {
    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SummaryRepository summaryRepository;

    public List<NotesEntity> findByUserId(UUID userId){
        return notesRepository.findByUserId(userId);
    }

    public NotesEntity saveNotes(NotesEntity notes){
        return notesRepository.save(notes);
    }

    public void deleteNotes(UUID notesId){
        notesRepository.deleteById(notesId);
    }

    public NotesEntity processFile(MultipartFile file, String name) throws IOException {
        UserEntity user = userRepository.findByUsername(name);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        String exctractedText = exctractTextFromPdf(file);
        String summary = generateSummary(exctractedText);

        NotesEntity notes = NotesEntity.builder()
                .user(user)
                .title(file.getOriginalFilename())
                .content(exctractedText)
                .created_at(LocalDateTime.now())
                .build();
        notesRepository.save(notes);

        SummaryEntity summaryEntity = SummaryEntity.builder()
                .note(notes)
                .user(user)
                .summaryText(summary)
                .modelUsed("Basic Extraction")
                .tokenUsed(summary.length())
                .createdAt(LocalDateTime.now())
                .build();
        summaryRepository.save(summaryEntity);

        return notes;
    }

    private String exctractTextFromPdf(MultipartFile file) throws IOException{
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }

    private String generateSummary(String text){
        String[] sentences = text.split("\\.");
        StringBuilder summary = new StringBuilder();

        for(int i =0 ;i < Math.min(3 , sentences.length); i++){
            summary.append(sentences[i]).append(". ");
        }
        return summary.toString();
    }
}
