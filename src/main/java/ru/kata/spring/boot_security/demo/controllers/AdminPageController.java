package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminPageController {

    private final UserService userService;

    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userService.findByUsername(principal.getName());
            model.addAttribute("allUsers", userService.getUsers());
            model.addAttribute("currentUser", currentUser);
        }
        return "admin";
    }


}
