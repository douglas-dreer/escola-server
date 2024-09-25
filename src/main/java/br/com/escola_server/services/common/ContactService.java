package br.com.escola_server.services.common;

import br.com.escola_server.models.ContactDTO;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    List<ContactDTO> findAll();

    ContactDTO findById(UUID id);

    ContactDTO save(ContactDTO dto);

    ContactDTO update(ContactDTO dto);

    void delete(UUID id);

    boolean existsById(UUID id);
}
