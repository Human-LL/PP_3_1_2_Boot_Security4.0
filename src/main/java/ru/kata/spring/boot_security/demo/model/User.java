package ru.kata.spring.boot_security.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class User implements Serializable, UserDetails {

    // Идентификатор пользователя
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Имя пользователя
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 10, message = "Username length must be between 2 and 10 characters")
    @Column(name = "username", unique = true)
    private String username;

    // Пароль пользователя
    @NotEmpty(message = "The password field must not be empty")
    @Size(min = 6, max = 18, message = "The password must be at least 6 and no more than 18 characters")
    @Column(name = "password")
    private String password;

    // Имя пользователя
    @NotEmpty(message = "Please provide a first name")
    @Size(min = 2, max = 26, message = "First name length must be between 2 and 26 characters")
    @Column(name = "first_name")
    private String firstName;

    // Фамилия пользователя
    @NotEmpty(message = "Please provide a last name")
    @Column(name = "last_name")
    private String lastName;

    // Email пользователя
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    // Роли пользователя
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Конструктор по умолчанию
    public User() {
    }

    // Конструктор с параметрами
    public User(String username, String password, String firstName, String lastName, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    // Получение ролей пользователя
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // Метод проверки срока действия учетной записи
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Метод проверки заблокирована ли учетная запись
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Метод проверки учётных данных на срок действия
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Метод проверки активирована ли учетная запись
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Переопределение метода для сравнения пользователей
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(roles, user.roles);
    }
}