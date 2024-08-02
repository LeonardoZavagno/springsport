package com.springsport.core.controllers.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/")
    public String greeting (Model model) {
        model.addAttribute("message", "Hello ".concat(retrieveLoggedinUserName()));
        return "greeting.jsp";
    }

	private String retrieveLoggedinUserName() {
		Object principal = SecurityContextHolder
            .getContext()
			.getAuthentication()
            .getPrincipal();

		if (principal instanceof UserDetails){
			return ((UserDetails) principal).getUsername();
        }

		return principal.toString();
	}

}
