package com.social.persitence;

import com.social.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDAO personDAO;

    @Override
    public Person findByName(String name) {
        return personDAO.findByName(name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePerson(Person person) {
        personDAO.save(person);
    }
}
