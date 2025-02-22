package com.example.summarizer.summarizer.Controller;

import com.example.summarizer.summarizer.Entity.NotesEntity;
import com.example.summarizer.summarizer.Entity.UserPrincipal;
import com.example.summarizer.summarizer.Service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path="api/files")
public class FileController {
    @Autowired
    private NotesService notesService;

    @PostMapping
    public ResponseEntity<?> upload(
            @RequestParam("file")
            MultipartFile file,
            @RequestHeader("Authorization") String token,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        try{
            System.out.println("File received: " + file.getOriginalFilename());
            System.out.println("Token received: " + token);
            System.out.println("UserPrincipal: " + userPrincipal);
            NotesEntity notes = notesService.processFile(file, userPrincipal.getUsername());
            return ResponseEntity.ok(notes);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error processing file " + e.getMessage());
        }
    }

}
