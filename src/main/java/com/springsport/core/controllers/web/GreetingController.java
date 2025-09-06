package com.springsport.core.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String greeting () {
        return "redirect:/greeting.html";
    }

}
