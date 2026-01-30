package com.github.dcassianodias.model.service;

import com.github.dcassianodias.data.dto.v1.PersonDTO;
import com.github.dcassianodias.data.dto.v2.PersonDTOV2;
import com.github.dcassianodias.exceptiom.ResourceNotFoundException;
import static com.github.dcassianodias.mapper.DozerMapper.parseListObjects;
import static com.github.dcassianodias.mapper.DozerMapper.parseObject;

import com.github.dcassianodias.mapper.custom.PersonMapper;
import com.github.dcassianodias.model.entities.Person;
import com.github.dcassianodias.model.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private PersonMapper mapper = new PersonMapper();

    public PersonDTO findById(Long id) {
        log.info("Finding one person!");
        var entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));
        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll() {
        log.info("Finding all persons!");
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDTO) {
        log.info("Creating one person!");
        var entity = parseObject(personDTO, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 personDTOV2) {
        log.info("Creating one person V2!");
        var entity = mapper.convertDtoToEntity(personDTOV2);
        return mapper.convertEntityToDtoV2(repository.save(entity));
    }

    public PersonDTO update(PersonDTO personDTO) {
        log.info("Updating one person!");
        Person entity = repository.findById(personDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);

    }

    public void delete(Long id) {
        log.info("Deleting one person!");
        Person person = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));

        repository.delete(person);

        log.info("Deleted one person!");

    }

}
