package com.example.summarizer.summarizer.Service;

import com.example.summarizer.summarizer.Entity.UserEntity;
import com.example.summarizer.summarizer.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> getUserById(UUID userId){
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    public void deleteUser(UUID userId){
        userRepository.deleteById(userId);
    }
}
