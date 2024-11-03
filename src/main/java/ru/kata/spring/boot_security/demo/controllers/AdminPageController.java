package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class AdminPageController {

    private final UserServiceImpl userServiceImpl;

    public AdminPageController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userServiceImpl.findByUsername(principal.getName());
            model.addAttribute("allUsers", userServiceImpl.getUsers());
            model.addAttribute("currentUser", currentUser);
        }
        return "admin";
    }


}
