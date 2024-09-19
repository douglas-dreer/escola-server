package br.com.escola_server.services;

import br.com.escola_server.entities.Person;
import br.com.escola_server.models.PersonDTO;
import br.com.escola_server.repositories.PersonRepository;
import br.com.escola_server.utilitaries.Converter;
import br.com.escola_server.utilitaries.DTOGenerator;
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
    private final Optional<Person> optionalEmptyResult = Optional.empty();
    private final String MSG_INTERNAL_ERROR = "There was an internal error. Please try again later.";
    @Mock
    private PersonRepository repository;
    @InjectMocks
    private PersonServiceImpl service;
    private UUID id;
    private Person result = new Person();
    private Optional<Person> optionalResult = Optional.empty();
    private PersonDTO dto;

    @BeforeEach
    public void setUp() {
        result = createPerson();
        id = result.getId();
        resultList.add(result);
        optionalResult = Optional.of(result);
        dto = Converter.convertTo(result, PersonDTO.class);
    }

    @Test
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(resultList);

        List<PersonDTO> personResultList = service.findAll();

        assertNotNull(personResultList);
        assertFalse(personResultList.isEmpty());

        verify(repository, times(1)).findAll();
    }

    @Test
    public void mustReturnExceptionWhenListEmpty() {
        when(repository.findAll()).thenThrow(new RuntimeException(MSG_INTERNAL_ERROR));
        List<PersonDTO> personResultList = service.findAll();

        verify(repository, times(1)).findAll();
        assertTrue(personResultList.isEmpty());
    }

    @Test
    public void mustReturnSuccessWhenFindById() {
        when(repository.findById(any())).thenReturn(optionalResult);

        PersonDTO personResult = service.findById(id);

        assertNotNull(personResult);
        assertEquals(id, personResult.getId());

        verify(repository, times(1)).findById(any());
    }

    @Test
    public void mustReturnNoResultWhenFindById() {
        when(repository.findById(any())).thenReturn(optionalEmptyResult);

        PersonDTO personResult = service.findById(id);

        assertNull(personResult);
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(result);

        PersonDTO personSaved = service.save(dto);

        assertNotNull(personSaved);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnExceptionWhenSave() {
        when(repository.save(any())).thenThrow(new RuntimeException(MSG_INTERNAL_ERROR));
        PersonDTO personSaved = service.save(dto);
        assertNull(personSaved);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnSuccessWhenUpdate() {
        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any())).thenReturn(result);

        PersonDTO personEdited = service.update(dto);

        assertNotNull(personEdited);
        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnNoResultExceptionWhenUpdate() {
        when(repository.existsById(any())).thenReturn(false);

        PersonDTO personEdited = service.update(dto);

        assertNull(personEdited);
        verify(repository, times(1)).existsById(any());
    }

    @Test
    public void mustReturnSuccessWhenDelete() {
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(any());

        service.delete(id);

        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    public void mustReturnNoResultExceptionWhenDelete() {
        when(repository.existsById(id)).thenReturn(false);
        service.delete(id);

        verify(repository, times(1)).existsById(any());
    }
}
