package com.mballem.demo_park_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class User implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name",nullable = false,unique = true,length = 100)
    private String username;
    @Column(name = "password",nullable = false,length = 200)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false,length = 25)
    private Role role = Role.CLIENT;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "modification_date")
    private LocalDate modificationDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;

    public enum Role{
         ADMIN,CLIENT
    }
}
