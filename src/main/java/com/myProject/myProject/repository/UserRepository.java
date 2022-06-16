package com.myProject.myProject.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.myProject.myProject.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
}
