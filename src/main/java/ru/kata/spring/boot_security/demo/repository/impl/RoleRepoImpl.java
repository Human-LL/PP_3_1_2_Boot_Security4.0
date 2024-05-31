package ru.kata.spring.boot_security.demo.repository.impl;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepoImpl implements RoleRepo {

    @PersistenceContext
    private EntityManager entityManager;

    // Возврат EntityManager для доступа к базе данных
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    // Получить роль по имени
    @Override
    public Role getRoleByName(String name) {
        Role role = null;
        try {
            role = getEntityManager()
                    .createQuery("SELECT r FROM Role r WHERE r.name=:name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Роли с именем " + name + " не существует!");
        } catch (NonUniqueResultException e) {
            System.out.println("Найдено несколько ролей с именем " + name + "!");
        }
        return role;
    }

    // Получить роль по ID
    @Override
    public Role getRoleById(Long id) {
        return getEntityManager().find(Role.class, id);
    }

    // Получить список всех ролей
    @Override
    public List<Role> allRoles() {
        return getEntityManager()
                .createQuery("select r from Role r", Role.class)
                .getResultList();
    }

        // Назначить роль пользователю
    @Override
    public void assignUserRole(Long userId, Role role) {
        try {
            User user = getEntityManager().find(User.class, userId);
            if (user != null) {
                Set<Role> userRoles = user.getRoles();
                if (userRoles == null) {
                    userRoles = new HashSet<>();
                }
                userRoles.add(role);
                user.setRoles(userRoles);
                getEntityManager().merge(user);
            } else {
                System.out.println("Пользователь с id " + userId + " не найден.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при назначении роли пользователю: " + e.getMessage());
        }
    }

    @Override
    public void addRole(Role role) {
        try {
            getEntityManager().persist(role);
        } catch (PersistenceException pe) {
            System.out.println("Ошибка при добавлении роли: " + pe.getMessage());
        }
    }
}
