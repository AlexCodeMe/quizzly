package com.alexcasey.quizzly.model;

import java.util.Collection;
import java.util.HashSet;

import com.alexcasey.quizzly.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true, nullable = false)
    private RoleEnum role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<Account> accounts = new HashSet<>();

    public Role(RoleEnum role) {
        this.role = role;
    }
}
