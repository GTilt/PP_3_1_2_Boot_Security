package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String userList(Model model, Principal principal) {
        model.addAttribute("allUsers", userServiceImpl.getUsers());
        User user = userServiceImpl.findByUsername(principal.getName());
        model.addAttribute("currentUser", user);
        return "admin";
    }

    @GetMapping("/admin/add")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") @Valid User user, Model model, @RequestParam String role, BindingResult bindingResult) {
       if(bindingResult.hasErrors()) {
           model.addAttribute("user", new User());
           return "admin";
       }

        if (userServiceImpl.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "admin";
        }
        userServiceImpl.addUser(user, role);
        System.out.println("Successfully added user");
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam("id") Long id, ModelMap model) {
        User user = userServiceImpl.findById(id);
        if (user != null) {
            model.addAttribute("editUser", user);
            return "admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("editUser") @Valid User user, @RequestParam String roleName, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("editUser", user);
            return "admin";
        }
        userServiceImpl.updateUser(user, roleName);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam Long id, ModelMap model) {
        User user = userServiceImpl.findById(id);
        if (user != null) {
            model.addAttribute("deleteUser", user);
            return "admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@ModelAttribute("deleteUser") User user, @RequestParam Long id) {
        userServiceImpl.deleteUser(userServiceImpl.findById(id));
        return "redirect:/admin";
    }
}
