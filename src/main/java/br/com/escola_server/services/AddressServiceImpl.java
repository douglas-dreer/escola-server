package br.com.escola_server.services;

import br.com.escola_server.entities.Address;
import br.com.escola_server.enums.StatusOperationType;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.AddressDTO;
import br.com.escola_server.repositories.AddressRepository;
import br.com.escola_server.services.common.AddressService;
import br.com.escola_server.utilitaries.Converter;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Log4j2
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<AddressDTO> findAll() {
        try {
            return repository
                    .findAll()
                    .stream()
                    .map(item -> Converter.convertTo(item, AddressDTO.class))
                    .toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(StatusOperationType.ERROR.getText());
        }
    }

    @Override
    public AddressDTO findById(UUID id) {
        try {
            Optional<Address> contact = repository.findById(id);

            if (contact.isEmpty()) {
                throw new NoResultException("No contact found with id: " + id);
            }

            return Converter.convertTo(contact, AddressDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddressDTO save(AddressDTO dto) {
        try {
            Address entity = Converter.convertTo(dto, Address.class);
            Address contactSaved = repository.save(entity);
            return Converter.convertTo(contactSaved, AddressDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(StatusOperationType.ERROR.getText());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddressDTO update(AddressDTO dto) {
        try {
            if (!existsById(dto.getId())) {
                throw new NoResultException(StatusOperationType.NOT_FOUND.getText());
            }

            Address entity = Converter.convertTo(dto, Address.class);
            Address contactEdited = repository.save(entity);
            return Converter.convertTo(contactEdited, AddressDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            if (!existsById(id)) {
                throw new NoResultException("No contact found with id: " + id);
            }
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
