package com.social.security;

import com.social.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
