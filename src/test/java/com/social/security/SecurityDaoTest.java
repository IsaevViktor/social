package com.social.security;

import com.social.SocialApplication;
import com.social.security.model.Role;
import com.social.security.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@DirtiesContext
@Transactional
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(classes = SocialApplication.class)
public class SecurityDaoTest {
    private Role role;
    private User user;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    public SecurityDaoTest() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        role = new Role("USER");
        user = new User("email@mail.ru",
                encoder.encode("password"),
                new HashSet<>(asList(role)),
                1);
    }

    @Before
    public void setUp() {
        entityManager.persist(role);
        entityManager.flush();

        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void testFindByEmail() {
        assertEquals(userDao.findByEmail("email@mail.ru").getEmail(), "email@mail.ru");
    }

    @Test
    public void testFindRole() {
        assertEquals(roleDao.findByRole("USER").getRole(), "USER");
    }
}