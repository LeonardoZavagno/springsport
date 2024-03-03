package com.springsport.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springsport.core.repositories.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("users")
    public String viewUsers (Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users.jsp";
    }

}
