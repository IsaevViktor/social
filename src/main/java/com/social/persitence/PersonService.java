package com.social.persitence;

import com.social.model.Person;

public interface PersonService {
    Person findByName(String name);

    void savePerson(Person person);
}
