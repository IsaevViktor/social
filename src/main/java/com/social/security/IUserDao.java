package com.social.security;


import com.social.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserDao extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
