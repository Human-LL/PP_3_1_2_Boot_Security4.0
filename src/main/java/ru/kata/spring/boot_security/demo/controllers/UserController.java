package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
@RequestMapping("/")
public class UserController {

    // Метод для отображения страницы входа
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Метод для отображения информации о пользователе
    @GetMapping("/user")
    public String userInfo(Model model, @AuthenticationPrincipal User user) {
        // Добавление текущего пользователя в модель для отображения на странице
        model.addAttribute("currentUser", user);
        return "user";
    }
}