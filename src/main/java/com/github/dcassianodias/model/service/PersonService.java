package com.github.dcassianodias.model.service;

import com.github.dcassianodias.exceptiom.ResourceNotFoundException;
import com.github.dcassianodias.model.entities.Person;
import com.github.dcassianodias.model.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PersonRepository repository;

    public Person findById(Long id) {
        log.info("Finding one person!");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public List<Person> findAll() {
        log.info("Finding all persons!");
        return repository.findAll();
    }

    public Person create(Person person) {
        log.info("Creating one person!");
        return repository.save(person);
    }

    public Person update(Person person) {
        log.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);

    }

    public void delete(Long id) {
        log.info("Deleting one person!");
        Person person = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));

        repository.delete(person);

        log.info("Deleted one person!");

    }

}
