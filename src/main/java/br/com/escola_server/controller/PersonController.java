package br.com.escola_server.controller;

import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.PersonDTO;
import br.com.escola_server.services.PersonService;
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
    private final PersonService service;

    public PersonController(PersonService service) throws BusinessException {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@RequestBody PersonDTO personDTO, HttpServletRequest request) throws URISyntaxException {
        PersonDTO personSaved = service.save(personDTO);
        URI location = new URI(String.format("%s/%s", request.getRequestURL(), personSaved.getId()));
        return ResponseEntity.created(location).body(personSaved);
    }

    @PatchMapping
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(service.update(personDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
