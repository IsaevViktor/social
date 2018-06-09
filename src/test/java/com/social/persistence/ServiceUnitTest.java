package com.social.persistence;

import com.social.persistence.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceUnitTest {

    @Configuration
    static class PersonTestConfiguration {
        @Primary
        @Bean
        PersonService testPersonService() {
            return new PersonServiceImpl();
        }
    }

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonDAO personDAO;

    @Before
    public void setUp() {
        Person person = new Person("Nick");
        Mockito.when(personDAO.findByName(person.getName())).thenReturn(person);
    }

    @Test
    public void testPersonService() {
        assertEquals(new Person("Nick").getName(), personService.findByName("Nick").getName() );

    }
}
