package br.com.escola_server.controller;

import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.ContactDTO;
import br.com.escola_server.services.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
@Log4j2
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) throws BusinessException {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> save(@RequestBody ContactDTO dto, HttpServletRequest request) throws URISyntaxException {
        ContactDTO contactSaved = service.save(dto);
        URI location = new URI(String.format("%s/%s", request.getRequestURL(), contactSaved.getId()));
        return ResponseEntity.created(location).body(contactSaved);
    }

    @PatchMapping
    public ResponseEntity<ContactDTO> update(@RequestBody ContactDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        service.delete(id);
    }
}
