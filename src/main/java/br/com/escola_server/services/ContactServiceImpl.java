package br.com.escola_server.services;

import br.com.escola_server.entities.Contact;
import br.com.escola_server.models.ContactDTO;
import br.com.escola_server.repositories.ContactRepository;

import br.com.escola_server.utilitaries.Converter;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            return new ArrayList<>();
        }
    }

    @Override
    public ContactDTO findById(UUID id) {
        try {
            Optional<Contact> contact = repository.findById(id);

            if (contact.isEmpty()) {
                throw new NoResultException("Person not found");
            }
            return Converter.convertTo(contact, ContactDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ContactDTO save(ContactDTO dto) {
        try {
            Contact entity = Converter.convertTo(dto, Contact.class);
            Contact contactSaved = repository.save(entity);
            return Converter.convertTo(contactSaved, ContactDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ContactDTO update(ContactDTO dto) {
        try {
            if (!existsById(dto.getId())) {
                throw new NoResultException("Person not found");
            }

            Contact entity = Converter.convertTo(dto, Contact.class);
            Contact contactEdited = repository.save(entity);
            return Converter.convertTo(contactEdited, ContactDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            if (!existsById(id)) {
                throw new NoResultException("Contact not found");
            }
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
