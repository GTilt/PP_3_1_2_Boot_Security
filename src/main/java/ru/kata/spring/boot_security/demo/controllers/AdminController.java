package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> userList(Principal principal) {
        List<User> users = userServiceImpl.getUsers();
        User currentUser = userServiceImpl.findByUsername(principal.getName());
        Map<String, Object> response = Map.of(
                "allUsers", users,
                "currentUser", currentUser
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
       if(userServiceImpl.findByUsername(user.getUsername()) != null) {
           return new ResponseEntity<>(HttpStatus.CONFLICT);
       }
        User createUser = userServiceImpl.addUser(user, user.getRoles());
        System.out.println("Successfully added user");
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userServiceImpl.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody @Valid User user) {
        if(userServiceImpl.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        userServiceImpl.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(userServiceImpl.findById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userServiceImpl.deleteUser(userServiceImpl.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
