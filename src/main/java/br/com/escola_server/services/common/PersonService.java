package br.com.escola_server.services.common;

import br.com.escola_server.models.PersonDTO;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    List<PersonDTO> findAll();

    PersonDTO findById(UUID id);

    PersonDTO save(PersonDTO dto);

    PersonDTO update(PersonDTO dto);

    void delete(UUID id) throws Exception;

    boolean existsById(UUID id);
}
