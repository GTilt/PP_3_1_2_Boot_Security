package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.Set;

@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/user")
    public String userGetInfo(Principal principal, Model model) {
        User user = userServiceImpl.findByUsername(principal.getName());
        Set<String> roles = AuthorityUtils.authorityListToSet(user.getRoles());
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user";
    }
}
