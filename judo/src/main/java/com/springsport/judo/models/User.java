package com.springsport.judo.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String user_name;

    private String user_surname;

    public User() {
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return this.user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(this.getUser_id(), user.getUser_id())
                && Objects.equals(this.getUser_name(), user.getUser_name())
                && Objects.equals(this.getUser_surname(), user.getUser_surname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUser_id(), this.getUser_name(), this.getUser_surname());
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("User{");
        sBuilder.append("id=");
        sBuilder.append(this.getUser_id());
        sBuilder.append(", name='");
        sBuilder.append(this.getUser_name());
        sBuilder.append('\'');
        sBuilder.append(", surname='");
        sBuilder.append(this.getUser_surname());
        sBuilder.append('\'');
        sBuilder.append('}');

        return sBuilder.toString();
    }

}
