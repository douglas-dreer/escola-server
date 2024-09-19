package br.com.escola_server.services;

import br.com.escola_server.models.PersonDTO;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    List<PersonDTO> findAll();

    PersonDTO findById(UUID id);

    PersonDTO save(PersonDTO person);

    PersonDTO update(PersonDTO person);

    void delete(UUID id) throws Exception;

    boolean existsById(UUID id);
}