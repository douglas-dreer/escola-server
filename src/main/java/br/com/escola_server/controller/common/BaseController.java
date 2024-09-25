package br.com.escola_server.controller.common;

import br.com.escola_server.entities.common.BaseEntity;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.common.BaseModel;
import br.com.escola_server.services.common.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Log4j2
public abstract class BaseController<T extends BaseEntity, M extends BaseModel, S extends BaseService<T, M>> {
    protected S service;

    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<M>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<M> findById(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (BusinessException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<M> save(@Validated @RequestBody M model, HttpServletRequest request) throws URISyntaxException {
        try {
            M savedEntity = service.save(model);
            URI location = new URI(String.format("%s/%s", request.getRequestURL(), savedEntity.getId()));
            return ResponseEntity.created(location).body(savedEntity);
        } catch (BusinessException e) {
            throw e;
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<M> edit(@PathVariable("uuid") UUID uuid, @Validated @RequestBody M model) throws JsonProcessingException {
        try {
            if (!model.getId().equals(uuid)) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Identification not equals");
            }

            M editedEntity = service.edit(model);
            return ResponseEntity.ok(editedEntity);
        } catch (BusinessException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) throws JsonProcessingException {
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (BusinessException e) {
            throw e;
        }
    }
}
