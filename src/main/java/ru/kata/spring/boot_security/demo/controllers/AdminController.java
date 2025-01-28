package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> userList(Principal principal) {
        List<User> users = userService.getUsers();
        User currentUser = userService.findByUsername(principal.getName());
        Map<String, Object> response = Map.of(
                "allUsers", users,
                "currentUser", currentUser
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        User createUser = userService.addUser(user, user.getRoles());
        System.out.println("Successfully added user");
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody @Valid User user) {
        user.setId(id);
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(userService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
