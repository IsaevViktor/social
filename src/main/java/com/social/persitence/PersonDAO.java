package com.social.persitence;

import com.social.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDAO extends JpaRepository<Person, Long>{
    Person findByName(String name);
}
