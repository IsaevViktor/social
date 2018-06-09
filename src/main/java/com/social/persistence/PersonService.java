package com.social.persistence;

import com.social.persistence.model.Person;

public interface PersonService {
    Person findByName(String name);

    void savePerson(Person person);
}
