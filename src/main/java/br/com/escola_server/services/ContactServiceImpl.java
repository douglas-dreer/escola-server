package br.com.escola_server.services;

import br.com.escola_server.entities.Contact;
import br.com.escola_server.enums.StatusOperationType;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.ContactDTO;
import br.com.escola_server.repositories.ContactRepository;

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
public class ContactServiceImpl implements ContactService {
    private final ContactRepository repository;

    public ContactServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<ContactDTO> findAll() {
        try {
            return repository
                    .findAll()
                    .stream()
                    .map(item -> Converter.convertTo(item, ContactDTO.class))
                    .toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(StatusOperationType.ERROR.getText());
        }
    }

    @Override
    public ContactDTO findById(UUID id) {
        try {
            Optional<Contact> contact = repository.findById(id);

            if (contact.isEmpty()) {
               throw new NoResultException("No contact found with id: " + id);
            }

            return Converter.convertTo(contact, ContactDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContactDTO save(ContactDTO dto) {
        try {
            Contact entity = Converter.convertTo(dto, Contact.class);
            Contact contactSaved = repository.save(entity);
            return Converter.convertTo(contactSaved, ContactDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(StatusOperationType.ERROR.getText());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContactDTO update(ContactDTO dto) {
        try {
            if (!existsById(dto.getId())) {
                throw new NoResultException(StatusOperationType.NOT_FOUND.getText());
            }

            Contact entity = Converter.convertTo(dto, Contact.class);
            Contact contactEdited = repository.save(entity);
            return Converter.convertTo(contactEdited, ContactDTO.class);
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
