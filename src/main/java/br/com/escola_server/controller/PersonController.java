package br.com.escola_server.controller;

import br.com.escola_server.models.PersonDTO;
import br.com.escola_server.services.common.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
@Log4j2
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@RequestBody PersonDTO personDTO, HttpServletRequest request) throws URISyntaxException {
        PersonDTO personSaved = personService.save(personDTO);
        URI location = new URI(String.format("%s/%s", request.getRequestURL(), personSaved.getId()));
        return ResponseEntity.created(location).body(personSaved);
    }

    @PatchMapping
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personService.update(personDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        personService.delete(id);
    }
}
