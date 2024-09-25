package br.com.escola_server.controller;

import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.AddressDTO;
import br.com.escola_server.services.common.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@Log4j2
public class AddressController {
    private final AddressService service;

    public AddressController(AddressService service) throws BusinessException {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> save(@RequestBody AddressDTO dto, HttpServletRequest request) throws URISyntaxException {
        AddressDTO contactSaved = service.save(dto);
        URI location = new URI(String.format("%s/%s", request.getRequestURL(), contactSaved.getId()));
        return ResponseEntity.created(location).body(contactSaved);
    }

    @PatchMapping
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
