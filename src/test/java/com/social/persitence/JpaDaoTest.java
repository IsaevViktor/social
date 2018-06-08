package com.social.persitence;

import com.social.SocialApplication;
import com.social.model.Address;
import com.social.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@DirtiesContext()
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SocialApplication.class)
public class JpaDaoTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private JpaPersonDao jpaPersonDao;

    @Transactional
    @Test
    public void testFindByName() {
        Person person = new Person();
        Address address = new Address();
        address.setCity("Moscow");
        address.setCountry("Russia");
        address.setStreet("Lenina");
        person.setAddress(address);
        jpaPersonDao.saveAndFlush(person);
        person.setName("Nick");
        assertEquals(jpaPersonDao.findByName("Nick").getName(), person.getName());
    }


    @Test
    @Transactional
    public void testPersistAddress() {
        Address address = new Address();
        address.setCity("Moscow");
        address.setCountry("Russia");
        address.setStreet("ul. Lenina");

        AddressDao jpaAddressDao = new JpaAddressDao(em);
        jpaAddressDao.saveAndFlush(address);
        assertEquals(jpaAddressDao.findAll().get(0).getCity(), address.getCity());

    }
}