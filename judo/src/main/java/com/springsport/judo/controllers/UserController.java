package com.springsport.judo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springsport.judo.models.User;
import com.springsport.judo.repositories.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public User get(@PathVariable Long id) {
        return userRepository.getReferenceById(id);
    }

    @PostMapping
    public User create(@RequestBody final User user){
        return userRepository.saveAndFlush(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.getReferenceById(id);
        BeanUtils.copyProperties(user, existingUser, "user_id");
        return userRepository.saveAndFlush(existingUser);
    }
}
