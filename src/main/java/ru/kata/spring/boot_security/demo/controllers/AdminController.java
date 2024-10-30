package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String userList(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getUsers());
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("currentUser", user);
//        User editUser = userService.findById(user.getId());
//        model.addAttribute("editUser", editUser);
//        User deleteUser = userService.findById(user.getId());
//        model.addAttribute("deleteUser", deleteUser);
        return "admin";
    }

    @GetMapping("/admin/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user, Model model, @RequestParam String role) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "admin";
        }
        userService.addUser(user, role);
        System.out.println("Successfully added user");
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("editUser", user);
            model.addAttribute("role", user.getRoles());
            return "admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("editUser") User user, @RequestParam String  roleName) {
        userService.updateUser(user, roleName);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam Long id, ModelMap model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("deleteUser", user);
            return "admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@ModelAttribute("deleteUser") User user, @RequestParam Long id) {
        userService.deleteUser(userService.findById(id));
        return "redirect:/admin";
    }
}
