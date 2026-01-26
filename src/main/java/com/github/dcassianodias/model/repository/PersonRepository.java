package com.github.dcassianodias.model.repository;

import com.github.dcassianodias.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
