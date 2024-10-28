package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.getUsers());
        return "admin";
    }

    @GetMapping("/admin/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        userService.addUser(user);
        System.out.println("Successfully added user");
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam Long id, ModelMap model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "edit";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam Long id, ModelMap model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "delete";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user")
    public String viewUser(@RequestParam Long id, ModelMap model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user";
        } else {
            return "redirect:/admin";
        }
    }
}
