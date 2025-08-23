package com.springsport.core.controllers.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsport.core.models.User;
import com.springsport.core.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list(){
        return userService.list();
    }

    @GetMapping("{id}")
    public User get(@PathVariable @NotNull Long id) {
        return userService.get(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody final User user){
        return userService.create(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @NotNull Long id) {
        userService.delete(id);
    }

    @PutMapping("{id}")
    public User update(@PathVariable @NotNull Long id, @Valid @RequestBody User user) {
        return userService.update(id, user);
    }
}
