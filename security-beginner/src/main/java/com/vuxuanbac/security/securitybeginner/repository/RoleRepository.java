package com.vuxuanbac.security.securitybeginner.repository;

import com.vuxuanbac.security.securitybeginner.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String name);
}
