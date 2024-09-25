package br.com.escola_server.services.common;

import br.com.escola_server.models.AddressDTO;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    List<AddressDTO> findAll();

    AddressDTO findById(UUID id);

    AddressDTO save(AddressDTO dto);

    AddressDTO update(AddressDTO dto);

    void delete(UUID id);

    boolean existsById(UUID id);
}
