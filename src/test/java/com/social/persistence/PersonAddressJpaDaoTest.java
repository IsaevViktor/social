package com.social.persistence;

import com.social.SocialApplication;
import com.social.persistence.model.Address;
import com.social.persistence.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@Transactional
@DirtiesContext()
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(classes = {SocialApplication.class})
public class PersonAddressJpaDaoTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private JpaPersonDao jpaPersonDao;

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