package com.alexcasey.quizzly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexcasey.quizzly.enums.RoleEnum;
import com.alexcasey.quizzly.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum role);
    boolean existsByRole(RoleEnum role);
}
