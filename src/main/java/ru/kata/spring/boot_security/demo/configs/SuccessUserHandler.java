package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Обработчик успешной аутентификации пользователя
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // Извлечение ролей пользователя из объекта Authentication
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Перенаправление пользователя в соответствии с его ролью
        if (roles.contains("ROLE_ADMIN")) {
            // Перенаправление на страницу "/admin" для пользователей с ролью "ROLE_ADMIN"
            httpServletResponse.sendRedirect("/admin");
        } else {
            // Перенаправление на страницу "/user" для остальных пользователей
            httpServletResponse.sendRedirect("/user");
        }
    }
}