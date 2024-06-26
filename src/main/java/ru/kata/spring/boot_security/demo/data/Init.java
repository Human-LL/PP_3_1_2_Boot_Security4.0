package ru.kata.spring.boot_security.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleSerivce;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleSerivce roleSerivce;

    @Autowired
    public Init(UserService userService, RoleSerivce roleSerivce) {
        this.userService = userService;
        this.roleSerivce = roleSerivce;
    }

    // Метод, выполняемый после создания объекта Init
    @PostConstruct
    public void init() {
        // Создание ролей
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");

        // Добавление ролей в базу данных
        roleSerivce.addRole(roleUser);
        roleSerivce.addRole(roleAdmin);

        // Создание и сохранение пользователей
        userService.saveUser(
                new User("user", "user", "terminator", 27, "user@mail.ru"),
                new HashSet<Role>(Set.of(roleAdmin, roleUser))
        );
        userService.saveUser(
                new User("user2", "user2", "tractor", 28, "user2@mail.ru")
        );
    }
}