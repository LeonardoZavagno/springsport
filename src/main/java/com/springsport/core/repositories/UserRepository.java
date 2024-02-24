package com.springsport.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsport.core.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
