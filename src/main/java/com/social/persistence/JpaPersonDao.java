package com.social.persistence;

import com.social.persistence.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

//@Transactional()
@Repository
public class JpaPersonDao extends SimpleJpaRepository<Person, Long> implements PersonDAO {
    private EntityManager em;

    @Autowired
    public JpaPersonDao(EntityManager entityManager) {
        super(Person.class, entityManager);
        this.em = entityManager;
    }

    @Transactional
    @Override
    public Person findByName(String name) {
        Query query = em.createNamedQuery("findByName", Person.class).setParameter("name", name);
        return (Person) query.getResultList().get(0);
    }
}
