package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;


    private  UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(EntityManager em, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.em = em;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();

    }


    public void deleteUser(User user) {
        if (user.getId() != null) {
            User existingUser = em.find(User.class, user.getId());
            if (existingUser != null) {
                em.remove(existingUser);
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public User addUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new EntityExistsException("User already exists");
        }
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(1L, "USER")));
        userRepository.save(user);
        return user;
    }
    public void updateUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null){
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                existingUser.setRoles(user.getRoles());
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this).passwordEncoder(bCryptPasswordEncoder);
    }


}
