package com.social.persistence;

import com.social.persistence.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDAO extends JpaRepository<Person, Long>{
    Person findByName(String name);
}
