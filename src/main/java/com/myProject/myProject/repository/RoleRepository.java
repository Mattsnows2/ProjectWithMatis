package com.myProject.myProject.repository;

import org.springframework.stereotype.Repository;

import com.myProject.myProject.models.ERoles;
import com.myProject.myProject.models.Roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long>{

    Optional<Roles> findByName(ERoles name);
    
}
