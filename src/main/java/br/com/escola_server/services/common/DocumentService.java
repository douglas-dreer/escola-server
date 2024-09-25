package br.com.escola_server.services.common;

import br.com.escola_server.models.DocumentDTO;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    List<DocumentDTO> findAll();

    DocumentDTO findById(UUID id);

    DocumentDTO save(DocumentDTO document);

    DocumentDTO update(DocumentDTO document);

    void delete(UUID id) throws Exception;

    boolean existsById(UUID id);
}
