package com.github.dcassianodias.model.service;

import com.github.dcassianodias.controllers.PersonController;
import com.github.dcassianodias.data.dto.v1.PersonDTO;
import com.github.dcassianodias.data.dto.v2.PersonDTOV2;
import com.github.dcassianodias.exceptiom.ResourceNotFoundException;
import com.github.dcassianodias.mapper.custom.PersonMapper;
import com.github.dcassianodias.model.entities.Person;
import com.github.dcassianodias.model.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.dcassianodias.mapper.DozerMapper.parseListObjects;
import static com.github.dcassianodias.mapper.DozerMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private static PersonMapper mapper = new PersonMapper();

    public PersonDTO findById(Long id) {
        log.info("Finding one person!");
        var entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> findAll() {
        log.info("Finding all persons!");
        var list = parseListObjects(repository.findAll(), PersonDTO.class);
        list.forEach(dto -> {
            try {
                addHateoasLinks(dto);
            } catch (Exception e) {
                log.error("Error adding HATEOAS links to person with ID: " + dto.getId(), e);
            }
        });
        return list;
    }

    public PersonDTO create(PersonDTO personDTO) {
        log.info("Creating one person!");
        var entity = parseObject(personDTO, Person.class);
        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
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

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);

        return dto;

    }

    public PersonDTO delete(Long id) {
        log.info("Deleting one person!");
        Person person = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(person, PersonDTO.class);

        repository.delete(person);

        addHateoasLinks(dto);

        log.info("Deleted one person!");

        return dto;
    }

    private static void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

    }



}
