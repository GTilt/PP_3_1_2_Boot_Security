package com.example.obshaga.controller;

import com.example.obshaga.entity.ERole;
import com.example.obshaga.entity.User;
import com.example.obshaga.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @GetMapping


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam Set<ERole> roles) {
        User user = authService.registerUser(username, password, roles);
        return ResponseEntity.ok("User registered: " + user.getUsername());
    }
}
