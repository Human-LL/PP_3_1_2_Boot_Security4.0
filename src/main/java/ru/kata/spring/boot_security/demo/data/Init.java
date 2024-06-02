package ru.kata.spring.boot_security.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init implements ApplicationRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public Init(RoleService roleService, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        saveRole();

        Role roleUser = roleService.getRoleByName("ROLE_USER");
        Role roleAdmin = roleService.getRoleByName("ROLE_ADMIN");

        Set<Role> rolesUser = new HashSet<>(Arrays.asList(roleUser));
        Set<Role> rolesAdmin = new HashSet<>(Arrays.asList(roleAdmin));

        User user1 = new User("user", passwordEncoder.encode("user"), "User", "user@example.com", rolesUser);
        userService.addUser(user1);

        User user2 = new User("admin", passwordEncoder.encode("admin"), "Admin", "admin@example.com", rolesAdmin);
        userService.addUser(user2);
    }

    private void saveRole() {
        if (roleService.getRoleByName("ROLE_USER") == null) {
            Role roleUser = new Role("ROLE_USER", "USER");
            roleService.addRole(roleUser);
        }
        if (roleService.getRoleByName("ROLE_ADMIN") == null) {
            Role roleAdmin = new Role("ROLE_ADMIN", "ADMIN");
            roleService.addRole(roleAdmin);
        }
    }
}