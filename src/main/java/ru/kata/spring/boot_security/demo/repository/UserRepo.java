package ru.kata.spring.boot_security.demo.repository;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepo {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

}