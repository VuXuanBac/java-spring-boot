package com.vuxuanbac.authenticator.repository;

import com.vuxuanbac.authenticator.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
