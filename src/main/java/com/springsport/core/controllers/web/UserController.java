package com.springsport.core.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springsport.core.models.User;
import com.springsport.core.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.list());
        return "users.jsp";
    }

    @PostMapping("/create")
    public RedirectView create(@ModelAttribute("user") @Valid @RequestBody final User user, RedirectAttributes redirectAttributes) {
        userService.create(user);
        
        final RedirectView redirectView = new RedirectView("/users", true);
        return redirectView;
    }
    

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable @NotNull Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        
        final RedirectView redirectView = new RedirectView("/users", true);
        return redirectView;
    } 
}
