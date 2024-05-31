package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;


public interface RoleRepo {

    Role getRoleByName(String name);

    Role getRoleById(Long id);

    List<Role> allRoles();

    void assignUserRole(Long userId, Role role);

    void addRole(Role role);
}