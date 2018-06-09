package com.social.security;

import com.social.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class UserDao extends SimpleJpaRepository<User, Long> implements IUserDao {
    EntityManager em;

    @Autowired
    public UserDao(EntityManager em) {
        super(User.class, em);
        this.em = em;
    }

    @Override
    public User findByEmail(String email) {
        Query query = em.createNamedQuery("findUserByEmail", User.class)
                .setParameter("email", email);
        return (User) query.getResultList().get(0);
    }
}
