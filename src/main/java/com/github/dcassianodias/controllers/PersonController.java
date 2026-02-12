package com.github.dcassianodias.controllers;

import com.github.dcassianodias.data.dto.v1.PersonDTO;
import com.github.dcassianodias.data.dto.v2.PersonDTOV2;
import com.github.dcassianodias.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @PostMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        PersonDTO savedPerson = service.create(personDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedPerson);
    }

    @PostMapping(value = "/v2", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<PersonDTOV2> createV2(@RequestBody PersonDTOV2 personDTO) {
        PersonDTOV2 savedPerson = service.createV2(personDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedPerson);
    }

    @PutMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO update(@RequestBody PersonDTO personDTO) {
        return service.update(personDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> delete(@PathVariable("id") Long id) {
        PersonDTO deleted = service.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
