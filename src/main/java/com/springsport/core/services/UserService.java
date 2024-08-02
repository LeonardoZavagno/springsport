package com.springsport.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springsport.core.controllers.exceptions.IdNullRequiredException;
import com.springsport.core.models.User;
import com.springsport.core.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;

    public List<User> list(){
        return userRepository.findAll();
    }

    public User get(@NotNull Long id) {
        Optional<User> oExistingUser = userRepository.findById(id);
        return oExistingUser.orElseThrow(EntityNotFoundException::new);
    }

    public User create(@Valid final User user){
        if(user.getUser_id()!=null){
            throw new IdNullRequiredException();
        }
        return userRepository.saveAndFlush(user);
    }

    public void delete(@NotNull Long id) {
        Optional<User> oExistingUser = userRepository.findById(id);
        userRepository.deleteById(oExistingUser.orElseThrow(EntityNotFoundException::new).getUser_id());
    }

    public User update(@NotNull Long id, @Valid User user) {
        if(user.getUser_id()!=null && !user.getUser_id().equals(id)){
            throw new IdNullRequiredException();
        }
        User existingUser = userRepository.getReferenceById(id);
        BeanUtils.copyProperties(user, existingUser, "user_id");
        return userRepository.saveAndFlush(existingUser);
    }

}
