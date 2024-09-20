package br.com.escola_server.controller;

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
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContactDTO> save(@RequestBody ContactDTO dto, HttpServletRequest request) throws URISyntaxException {
        ContactDTO contactSaved = contactService.save(dto);
        URI location = new URI(String.format("%s/%s", request.getRequestURL(), contactSaved.getId()));
        return ResponseEntity.created(location).body(contactSaved);
    }

    @PatchMapping
    public ResponseEntity<ContactDTO> update(@RequestBody ContactDTO dto) {
        return ResponseEntity.ok(contactService.update(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        contactService.delete(id);
    }
}
