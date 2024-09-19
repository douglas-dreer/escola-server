package br.com.escola_server.services;

import br.com.escola_server.entities.Person;
import br.com.escola_server.models.PersonDTO;
import br.com.escola_server.repositories.PersonRepository;
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
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public List<PersonDTO> findAll() {
        try {
            return personRepository
                    .findAll()
                    .stream()
                    .map(item -> Converter.convertTo(item, PersonDTO.class))
                    .toList();

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public PersonDTO findById(UUID id) {
        try {
            Optional<Person> personId = personRepository.findById(id);

            if (personId.isEmpty()) {
                throw new NoResultException("Person not found");
            }
            return Converter.convertTo(personId.get(), PersonDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public PersonDTO save(PersonDTO person) {
        try {
            Person personSaved = personRepository.save(Converter.convertTo(person, Person.class));
            return Converter.convertTo(personSaved, PersonDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public PersonDTO update(PersonDTO person) {
        try {
            if (!existsById(person.getId())) {
                throw new NoResultException("Person not found");
            }

            Person entity = Converter.convertTo(person, Person.class);
            Person entitySaved = personRepository.save(entity);
            return Converter.convertTo(entitySaved, PersonDTO.class);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            if (!existsById(id)) {
                throw new NoResultException("Person not found");
            }
            personRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

    }

    @Override
    public boolean existsById(UUID id) {
        return personRepository.existsById(id);
    }
}
