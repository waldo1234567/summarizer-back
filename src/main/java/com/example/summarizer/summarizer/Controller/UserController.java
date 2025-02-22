package com.example.summarizer.summarizer.Controller;

import com.example.summarizer.summarizer.Entity.UserEntity;
import com.example.summarizer.summarizer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody UserEntity user){
        UserEntity savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity user){
        try{
           String token =  userService.verify(user);
           return ResponseEntity.ok(token);
        }catch (Exception e){
            throw new UsernameNotFoundException("invalid username or password");
        }
    }
}
