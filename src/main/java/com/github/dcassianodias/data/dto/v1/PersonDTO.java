package com.github.dcassianodias.data.dto.v1;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.dcassianodias.serializer.GenderSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@JsonPropertyOrder({
        "id",
        "first_name",
        "last_name",
        "birthDate",
        "address",
        "gender"
})
//@JsonFilter("PersonFilter")
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phone;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private String address;

    @JsonSerialize(using = GenderSerialize.class)
    private String gender;

    public PersonDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName())
                && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getPhone(),
                personDTO.getPhone()) && Objects.equals(getBirthDate(), personDTO.getBirthDate()) &&
                Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(),
                personDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhone(), getBirthDate(), getAddress(),
                getGender());
    }
}
