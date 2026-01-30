package com.github.dcassianodias.mapper.custom;

import com.github.dcassianodias.data.dto.v2.PersonDTOV2;
import com.github.dcassianodias.model.entities.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDtoV2(Person person){
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(new Date());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        return dto;
    }

    public Person convertDtoToEntity(PersonDTOV2 personDTOV2){
        Person person = new Person();
        person.setId(personDTOV2.getId());
        person.setFirstName(personDTOV2.getFirstName());
        person.setLastName(personDTOV2.getLastName());
        //person.setBirthDate(newDate());
        person.setAddress(personDTOV2.getAddress());
        person.setGender(personDTOV2.getGender());
        return person;
    }


}
