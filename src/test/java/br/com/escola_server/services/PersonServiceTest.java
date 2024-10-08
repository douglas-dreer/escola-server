package br.com.escola_server.services;

import br.com.escola_server.entities.Person;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.PersonDTO;
import br.com.escola_server.repositories.PersonRepository;
import br.com.escola_server.utilitaries.Converter;
import br.com.escola_server.utilitaries.DTOGenerator;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest extends DTOGenerator {
    private final List<Person> resultList = new ArrayList<>();
    private final String MSG_INTERNAL_ERROR = "There was an internal error. Please try again later.";
    @Mock
    private PersonRepository repository;
    @InjectMocks
    private PersonServiceImpl service;
    private UUID id;
    private Person result = new Person();
    private PersonDTO dto;

    @BeforeEach
    void setUp() {
        result = createPerson();
        id = result.getId();
        resultList.add(result);
        dto = Converter.convertTo(result, PersonDTO.class);
    }

    @Test
    void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(resultList);

        List<PersonDTO> personResultList = service.findAll();

        assertNotNull(personResultList);
        assertFalse(personResultList.isEmpty());

        verify(repository, times(1)).findAll();
    }

    @Test
    void mustReturnBusinessExceptionWhenFindAll() {
        when(repository.findAll()).thenThrow(new RuntimeException(MSG_INTERNAL_ERROR));

        assertThrows(BusinessException.class, () -> service.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    void mustReturnSuccessWhenFindById() {
        Optional<Person> optionalResult = Optional.of(result);
        when(repository.findById(any())).thenReturn(optionalResult);

        PersonDTO personResult = service.findById(id);

        assertNotNull(personResult);
        assertEquals(id, personResult.getId());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void mustReturnNoResultWhenFindById() {
        Optional<Person> optionalEmptyResult = Optional.empty();
        when(repository.findById(any())).thenReturn(optionalEmptyResult);

        assertThrows(NoResultException.class, () -> service.findById(id));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(result);

        PersonDTO personSaved = service.save(dto);

        assertNotNull(personSaved);
        verify(repository, times(1)).save(any());
    }

    @Test
    void mustReturnExceptionWhenSave() {
        when(repository.save(any())).thenThrow(new RuntimeException(MSG_INTERNAL_ERROR));

        assertThrows(BusinessException.class, () -> service.save(dto));
        verify(repository, times(1)).save(any());
    }

    @Test
    void mustReturnSuccessWhenUpdate() {
        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any())).thenReturn(result);

        PersonDTO personEdited = service.update(dto);

        assertNotNull(personEdited);
        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void mustReturnNoResultExceptionWhenUpdate() {
        when(repository.existsById(any())).thenReturn(false);

        assertThrows(NoResultException.class, () -> service.update(dto));
        verify(repository, times(1)).existsById(any());
    }

    @Test
    void mustReturnSuccessWhenDelete() {
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(any());

        service.delete(id);

        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void mustReturnNoResultExceptionWhenDelete() {
        when(repository.existsById(id)).thenReturn(false);
        assertThrows(NoResultException.class, () -> service.delete(id));
        verify(repository, times(1)).existsById(any());
    }
}
