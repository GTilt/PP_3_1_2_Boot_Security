package com.example.obshaga.service.impl;

import com.example.obshaga.entity.ERole;
import com.example.obshaga.entity.Role;
import com.example.obshaga.entity.User;
import com.example.obshaga.repository.RoleRepository;
import com.example.obshaga.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User registerUser(String username, String password, Set<ERole> roleNames) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        for (ERole roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
