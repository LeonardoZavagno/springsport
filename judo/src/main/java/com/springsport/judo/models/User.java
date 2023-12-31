package com.springsport.judo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

    @NotNull(message = "The user_id number is required.")
    @Positive(message = "The user_id must be greater than 0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    
    @NotNull(message = "The user_name is required.")
    private String user_name;

    @NotNull(message = "The user_surname is required.")
    private String user_surname;
    
    public User(){
    }

    public User(@NotNull @Positive Long user_id, @NotNull String user_name, @NotNull String user_surname){
        this.setUser_id(user_id);
        this.setUser_name(user_name);
        this.setUser_surname(user_surname);
    }
}
