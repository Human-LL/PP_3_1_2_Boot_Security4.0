package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role implements GrantedAuthority {

    private String description;

    // Идентификатор роли
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название роли
    private String name;

    // Набор пользователей, которым назначена эта роль
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role() {

    }

    // Получение наименования роли как названия ее полномочий
    @Override
    public String getAuthority() {
        return getName();
    }

    // Переопределение метода для сравнения ролей
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(users, role.users);
    }

    // Переопределение метода для вычисления хеш-кода роли
    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }

    // Переопределение метода для удобного вывода информации о роли
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }

}