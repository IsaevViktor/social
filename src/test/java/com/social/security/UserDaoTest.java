package com.social.security;

import com.social.model.security.Role;
import com.social.model.security.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@DirtiesContext
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest
public class UserDaoTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    IUserDao userDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        Role role = new Role("USER");
        entityManager.persist(role);
        entityManager.flush();
        User user = new User("email@mail.ru",
                encoder.encode("password"),
                new HashSet<>(asList(role)));
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void testFindByEmail() {
        assertEquals(userDao.findByEmail("email@mail.ru").getEmail(), "email@mail.ru");
    }
}