package com.springsport.core.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users")
    public String showUsersPage() { return "redirect:/users.html"; }
}