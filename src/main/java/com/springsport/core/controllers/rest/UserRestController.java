package com.springsport.core.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsport.core.controllers.exceptions.IdNullRequiredException;
import com.springsport.core.models.User;
import com.springsport.core.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> list(){
        return userRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public User get(@PathVariable @NotNull Long id) {
        Optional<User> oExistingUser = userRepository.findById(id);
        
        return oExistingUser.orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public User create(@Valid @RequestBody final User user){
        if(user.getUser_id()!=null){
            throw new IdNullRequiredException();
        }
        return userRepository.saveAndFlush(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @NotNull Long id) {
        Optional<User> oExistingUser = userRepository.findById(id);

        userRepository.deleteById(oExistingUser.orElseThrow(EntityNotFoundException::new).getUser_id());
    }

    @PutMapping("{id}")
    public User update(@PathVariable @NotNull Long id, @Valid @RequestBody User user) {
        if(user.getUser_id()!=null && !user.getUser_id().equals(id)){
            throw new IdNullRequiredException();
        }
        User existingUser = userRepository.getReferenceById(id);
        BeanUtils.copyProperties(user, existingUser, "user_id");
        return userRepository.saveAndFlush(existingUser);
    }
}
