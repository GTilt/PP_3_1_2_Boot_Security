package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            userRepository.delete(existingUser);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }


    @Override
    @Transactional
    public User addUser(User user, Set<Role> roleName) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new EntityExistsException("User already exists");
        }
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setAge(user.getAge());
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> existingRoles = new HashSet<>();
        for (Role role : roleName) {
            Role existingRole = roleService.findByName(role.getName());
            if (existingRole != null) {
                existingRoles.add(existingRole);
            }
        }
        user.setRoles(existingRoles);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        Set<Role> currentRoles = existingUser.getRoles();

        Set<Role> newRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleService.findByName(role.getName());
            if (existingRole != null) {
                newRoles.add(existingRole);
            } else {
                throw new EntityNotFoundException("Role not found: " + role.getName());
            }
        }
        currentRoles.removeAll(newRoles);
        currentRoles.addAll(newRoles);
        existingUser.setRoles(currentRoles);

        userRepository.save(existingUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
