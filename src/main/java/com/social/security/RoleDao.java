package com.social.security;

import com.social.security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class RoleDao extends SimpleJpaRepository<Role, Long> implements IRoleDao {
    private EntityManager em;

    @Autowired
    public RoleDao(EntityManager em) {
        super(Role.class, em);
        this.em = em;
    }

    @Override
    public Role findByRole(String role) {
        Query query = em.createNamedQuery("findByRole", Role.class).setParameter("role", role);
        return (Role) query.getResultList().get(0);
    }
}
