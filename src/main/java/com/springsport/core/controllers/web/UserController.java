package com.springsport.core.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springsport.core.models.User;
import com.springsport.core.repositories.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userRepository.findAll());
        return "users.jsp";
    }

    @PostMapping("/adduser")
    public RedirectView addUser(@ModelAttribute("user") @Valid @RequestBody final User user, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/users", true);
        userRepository.saveAndFlush(user);
        
        return redirectView;
    } 
}
