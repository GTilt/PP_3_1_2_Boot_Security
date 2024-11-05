package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 2, max = 25, message = "min 2 - max 5")
    @Column
    private String username;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 3, message = "не меньше 3")
    @Column
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 2, max = 25, message = "min 2 - max 5")
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 2, max = 25, message = "min 2 - max 5")
    @Column(name = "lastname")
    private String lastName;

    @Min(value = 10, message = "Не младше 10")
    @Max(value = 100, message = "Не #^$@!")
    @Column(name = "age")
    private int age;

    @Email(message = "должна быть @")
    @NotEmpty(message = "Не пустая строка")
    @Column(name = "email")
    private String email;

    public User() {

    }

    public User(String username, List<String> roles) {
        this.username = username;
        this.roles = new HashSet<>();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public @NotEmpty(message = "Не должно быть пустым") @Size(min = 2, max = 25, message = "min 2 - max 5") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Не должно быть пустым") @Size(min = 2, max = 25, message = "min 2 - max 5") String username) {
        this.username = username;
    }

    @Override
    public @NotEmpty(message = "Не должно быть пустым") @Size(min = 3, message = "не меньше 3") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Не должно быть пустым") @Size(min = 3, message = "не меньше 3") String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public @NotEmpty(message = "Не должно быть пустым") @Size(min = 2, max = 25, message = "min 2 - max 5") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty(message = "Не должно быть пустым") @Size(min = 2, max = 25, message = "min 2 - max 5") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "Не должно быть пустым") @Size(min = 2, max = 25, message = "min 2 - max 5") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "Не должно быть пустым") @Size(min = 2, max = 25, message = "min 2 - max 5") String lastName) {
        this.lastName = lastName;
    }

    @Min(value = 10, message = "Не младше 10")
    @Max(value = 100, message = "Не #^$@!")
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 10, message = "Не младше 10") @Max(value = 100, message = "Не #^$@!") int age) {
        this.age = age;
    }

    public @Email(message = "должна быть @") @NotEmpty(message = "Не пустая строка") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "должна быть @") @NotEmpty(message = "Не пустая строка") String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        if (id != null && user.id != null) {
            return Objects.equals(id, user.id);
        }

        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id != null ? id : username);
    }

    @Override
    public String toString() {
        return roles.toString();
    }
}
