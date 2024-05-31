package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepo;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(User user) {
        encryptUserPassword(user);
        userRepository.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public void editUser(User user) {
        if (userRepository.getUserById(user.getId()) == null) {
            throw new EntityNotFoundException("User with id " + user.getId() + " not found");
        }
        encryptUserPassword(user);
        userRepository.editUser(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public void setRoles(User user, String roleAdmin, String roleUser) {
        Set<String> roleNames = new HashSet<>();
        roleNames.add("ROLE_USER");

        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roleNames.add("ROLE_ADMIN");
        }

        if (roleUser != null && roleUser.equals("ROLE_USER")) {
            roleNames.add("ROLE_USER");
        }

        Set<Role> roles = convertRoleNamesToRoles(roleNames);

        user.setRoles(roles);
    }

    public Set<Role> convertRoleNamesToRoles(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = new Role();
            role.setName(roleName);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public void encryptUserPassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}