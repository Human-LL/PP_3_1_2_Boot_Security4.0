package ru.kata.spring.boot_security.demo.repository.impl;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    // Получение экземпляра EntityManager
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    // Добавление нового пользователя
    @Override
    @Transactional
    public void addUser(User user) {
        getEntityManager().persist(user);
    }

    // Удаление пользователя по ID
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getEntityManager().find(User.class, id);
        if (user != null) {
            getEntityManager().remove(user);
        } else {
            throw new EntityNotFoundException("Пользователь с id " + id + " не существует!");
        }
    }

    // Редактирование пользователя
    @Override
    @Transactional
    public void editUser(User user) {
        getEntityManager().merge(user);
    }

    // Получение пользователя по ID
    @Override
    public User getUserById(Long id) {
        User user = getEntityManager().find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с id " + id + " не найден.");
        }
        return user;
    }

    // Получение списка всех пользователей
    @Override
    public List<User> getAllUsers() {
        return getEntityManager()
                .createQuery("select u from User u", User.class)
                .getResultList();
    }


    // Получение пользователя по его имени
    @Override
    public User getUserByUsername(String username) {
        return getEntityManager()
                .createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();

    }
}
