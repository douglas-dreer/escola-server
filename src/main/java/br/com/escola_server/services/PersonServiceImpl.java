package br.com.escola_server.services;

import br.com.escola_server.entities.Person;
import br.com.escola_server.enums.StatusOperationType;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.PersonDTO;
import br.com.escola_server.repositories.PersonRepository;
import br.com.escola_server.services.common.PersonService;
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
public class PersonServiceImpl implements PersonService {
    private static final String MSG_NOT_FOUND = "Person not found";
    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<PersonDTO> findAll() {
        try {
            return repository
                    .findAll()
                    .stream()
                    .map(item -> Converter.convertTo(item, PersonDTO.class))
                    .toList();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(StatusOperationType.ERROR.getText());
        }
    }

    @Override
    public PersonDTO findById(UUID id) {
        try {
            Optional<Person> personId = repository.findById(id);

            if (personId.isEmpty()) {
                throw new NoResultException(MSG_NOT_FOUND);
            }
            return Converter.convertTo(personId.get(), PersonDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonDTO save(PersonDTO dto) {
        try {
            Person personSaved = repository.save(Converter.convertTo(dto, Person.class));
            return Converter.convertTo(personSaved, PersonDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new BusinessException(StatusOperationType.ERROR.getText());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonDTO update(PersonDTO dto) {
        try {
            if (!existsById(dto.getId())) {
                throw new NoResultException(MSG_NOT_FOUND);
            }

            Person entity = Converter.convertTo(dto, Person.class);
            Person personEdited = repository.save(entity);
            return Converter.convertTo(personEdited, PersonDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) {
        try {
            if (!existsById(id)) {
                throw new NoResultException(MSG_NOT_FOUND);
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
