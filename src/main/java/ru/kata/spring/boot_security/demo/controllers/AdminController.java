package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/admin")
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

    @GetMapping("/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin";
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

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") Long id, ModelMap model) {
        User user = userServiceImpl.findById(id);
        if (user != null) {
            model.addAttribute("editUser", user);
            return "admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody @Valid User user) {
        if(userServiceImpl.findByUsername(user.getUsername()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userServiceImpl.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id, ModelMap model) {
        User user = userServiceImpl.findById(id);
        if (user != null) {
            model.addAttribute("deleteUser", user);
            return "admin";
        } else {
            return "redirect:/admin";
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        if(userServiceImpl.findById(user.getId()) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userServiceImpl.deleteUser(userServiceImpl.findById(user.getId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
