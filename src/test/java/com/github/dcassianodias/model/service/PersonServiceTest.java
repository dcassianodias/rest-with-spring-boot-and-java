package com.github.dcassianodias.model.service;

import com.github.dcassianodias.data.dto.v1.PersonDTO;
import com.github.dcassianodias.exceptiom.ResourceNotFoundException;
import com.github.dcassianodias.mapper.custom.PersonMapper;
import com.github.dcassianodias.model.entities.Person;
import com.github.dcassianodias.model.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private PersonService service;

    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setFirstName("Danilo");
        person.setLastName("Dias");
        person.setAddress("Brazil");
        person.setGender("M");

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("Danilo");
        personDTO.setLastName("Dias");
        personDTO.setAddress("Brazil");
        personDTO.setGender("M");
    }

    @Test
    void findById_ShouldReturnPersonDTO_WhenIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        PersonDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenIdNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.findById(1L));

        verify(repository).findById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfPersonDTO() {
        when(repository.findAll()).thenReturn(List.of(person));

        List<PersonDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void create_ShouldReturnPersonDTO_WhenPersonIsCreated(){
        when(repository.save(any(Person.class))).thenReturn(person);

        PersonDTO result = service.create(personDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any(Person.class));
    }

    @Test
    void update_ShouldReturnPersonDTO_WhenIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(any(Person.class))).thenReturn(person);

        PersonDTO result = service.update(personDTO);

        assertNotNull(result);
        assertEquals("Danilo", result.getFirstName());
        assertEquals("M", result.getGender());
        verify(repository).findById(1L);
        verify(repository).save(any(Person.class));
    }

    @Test
    void update_ShouldReturnPersonDTO_WhenIdNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(personDTO));
        verify(repository).findById(1L);
    }

    @Test
    void delete_ShouldReturnDeletedPersonDTO_WhenIdExists(){
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        PersonDTO result = service.delete(1L);

        assertNotNull(result);
        verify(repository).delete(person);
    }

    @Test
    void delete_ShouldThrowException_WhenIdNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(1L));

        verify(repository).findById(1L);
    }

}