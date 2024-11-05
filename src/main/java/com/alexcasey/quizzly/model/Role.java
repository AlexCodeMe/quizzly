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

    // no args constructor
    public Role() {
    }

    // all args constructor
    public Role(Long id, RoleEnum role, Collection<Account> accounts) {
        this.id = id;
        this.role = role;
        this.accounts = accounts;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public RoleEnum getRole() {
        return role;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return id != null && id.equals(role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
