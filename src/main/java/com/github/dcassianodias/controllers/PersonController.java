package com.github.dcassianodias.controllers;

import com.github.dcassianodias.data.dto.v1.PersonDTO;
import com.github.dcassianodias.data.dto.v2.PersonDTOV2;
import com.github.dcassianodias.model.service.PersonService;
import com.github.dcassianodias.openapi.annotations.ApiResponsesCommon;
import com.github.dcassianodias.openapi.annotations.ApiResponsesCommonNotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(
        value = "/api/person/v1",
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE
        }
)
@Tag(name = "People", description = "Endpoints for managing people")
@ApiResponsesCommon
public class PersonController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String XML  = MediaType.APPLICATION_XML_VALUE;
    private static final String YAML = MediaType.APPLICATION_YAML_VALUE;

    @Autowired
    private PersonService service;

    @GetMapping("/{id}")
    @ApiResponsesCommonNotFound
    @Operation(
            summary = "Find a person by ID",
            description = "Returns a specific person by its ID.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    )
            }
    )
    public PersonDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    @Operation(
            summary = "Find all people",
            description = "Returns a list of all people.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = JSON,
                                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                                    ),
                                    @Content(
                                            mediaType = XML,
                                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                                    ),
                                    @Content(
                                            mediaType = YAML,
                                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                                    )
                            }
                    )
            }
    )
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = {JSON, XML, YAML})
    @Operation(
            summary = "Create a new person (v1)",
            description = "Creates a new person and returns it. Also returns a Location header pointing to the created resource.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    )
            }
    )
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        PersonDTO savedPerson = service.create(personDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedPerson);
    }

    @PostMapping(value = "/v2", consumes = {JSON, XML, YAML})
    @Operation(
            summary = "Create a new person (v2)",
            description = "Creates a new person using the V2 payload and returns it. Also returns a Location header " +
                    "pointing to the created resource.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(schema = @Schema(implementation = PersonDTOV2.class))
                    )
            }
    )
    public ResponseEntity<PersonDTOV2> createV2(@RequestBody PersonDTOV2 personDTO) {
        PersonDTOV2 savedPerson = service.createV2(personDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedPerson);
    }

    @PutMapping(consumes = {JSON, XML, YAML})
    @ApiResponsesCommonNotFound
    @Operation(
            summary = "Update a person",
            description = "Updates an existing person and returns the updated resource.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    )
            }
    )
    public PersonDTO update(@RequestBody PersonDTO personDTO) {
        return service.update(personDTO);
    }

    @DeleteMapping("/{id}")
    @ApiResponsesCommonNotFound
    @Operation(
            summary = "Delete a person by ID",
            description = "Deletes a person by its ID and returns the deleted resource.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    )
            }
    )
    public ResponseEntity<PersonDTO> delete(@PathVariable Long id) {
        PersonDTO deleted = service.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
