package com.myProject.myProject.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProject.myProject.models.User;
import com.myProject.myProject.repository.UserRepository;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    
    public List<User> findAllUsers() {
        return StreamSupport
        .stream(userRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return ResponseEntity.ok().body(user.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }
    
}
