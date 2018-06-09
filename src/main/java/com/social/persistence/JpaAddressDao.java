package com.social.persistence;

import com.social.persistence.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class JpaAddressDao extends SimpleJpaRepository<Address, Long> implements AddressDao{
    private EntityManager em;

    @Autowired
    public JpaAddressDao(EntityManager em) {
        super(Address.class, em);
        this.em = em;
    }
}
