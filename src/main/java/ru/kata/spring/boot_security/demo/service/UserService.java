package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

    void setRoles(User user, String roleAdmin, String roleUser);

    void encryptUserPassword(User user);

}