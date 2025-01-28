package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserInfo(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Set<String> roles = AuthorityUtils.authorityListToSet(user.getRoles());
        Map<String, Object> response = Map.of(
                "user", user,
                "roles", roles
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
