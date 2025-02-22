package com.example.summarizer.summarizer.Service;

import com.example.summarizer.summarizer.Entity.UserEntity;
import com.example.summarizer.summarizer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTservice jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepository userRepository, JWTservice jwtService, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public Optional<UserEntity> getUserById(UUID userId){
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserEntity saveUser(UserEntity user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(UserEntity user){
        UserEntity existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            System.out.println("User not found");
            return "errors";
        }

        if (!encoder.matches(user.getPassword(), existingUser.getPassword())) {
            System.out.println("Password does not match!");
            return "errors";
        }

        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("user authenticated");
            return jwtService.generateToken(user.getUsername());
        }

        return "errors";
    }
    public void deleteUser(UUID userId){
        userRepository.deleteById(userId);
    }
}
