package br.com.escola_server.controller;

import br.com.escola_server.models.DocumentDTO;
import br.com.escola_server.services.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
@Log4j2
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {this.documentService = documentService;}

    @GetMapping
    public ResponseEntity<List<DocumentDTO>> findAll() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(documentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DocumentDTO> save(@RequestBody DocumentDTO documentDTO, HttpServletRequest request) throws URISyntaxException {
        DocumentDTO documentSaved = documentService.save(documentDTO);
        URI location = new URI(String.format("%s/%s", request.getRequestURL(), documentSaved.toString()));
        return ResponseEntity.created(location).body(documentSaved);
    }

    @PutMapping
    public ResponseEntity<DocumentDTO> update(@RequestBody DocumentDTO documentDTO) {
        return ResponseEntity.ok(documentService.update(documentDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) throws Exception {
        documentService.delete(id);
    }
}
