package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void deleteUser(User user);

    User addUser(User user, String roleName);

    void updateUser(User user, String roleName);

    User findByUsername(String username);

    User findById(Long id);
}
