package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String UserInfo(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Set<String> roles = AuthorityUtils.authorityListToSet(user.getRoles());
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user";
    }

}
