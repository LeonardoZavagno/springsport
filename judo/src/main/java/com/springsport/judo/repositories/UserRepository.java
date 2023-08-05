package com.springsport.judo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsport.judo.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
