package br.com.escola_server.services;

import br.com.escola_server.entities.Contact;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.ContactDTO;
import br.com.escola_server.repositories.ContactRepository;
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
public class ContactServiceTest extends DTOGenerator {
    private final List<Contact> resultList = new ArrayList<>();
    private final String MSG_INTERNAL_ERROR = "There was an internal error. Please try again later.";
    @Mock
    private ContactRepository repository;
    @InjectMocks
    private ContactServiceImpl service;
    private UUID id;
    private Contact result = new Contact();
    private ContactDTO dto;

    @BeforeEach
    public void setUp() {
        result = createContact();
        id = result.getId();
        resultList.add(result);
        dto = Converter.convertTo(result, ContactDTO.class);
    }

    @Test
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(resultList);

        List<ContactDTO> contactResultList = service.findAll();

        assertNotNull(contactResultList);
        assertFalse(contactResultList.isEmpty());

        verify(repository, times(1)).findAll();
    }

    @Test
    public void mustReturnBusinessExceptionWhenFindAll() {
        when(repository.findAll()).thenThrow(new RuntimeException(MSG_INTERNAL_ERROR));

        assertThrows(BusinessException.class, () -> service.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void mustReturnSuccessWhenFindById() {
        Optional<Contact> optionalResult = Optional.of(result);
        when(repository.findById(any())).thenReturn(optionalResult);

        ContactDTO contactResult = service.findById(id);

        assertNotNull(contactResult);
        assertEquals(id, contactResult.getId());

        verify(repository, times(1)).findById(any());
    }

    @Test
    public void mustReturnNoResultWhenFindById() {
        Optional<Contact> optionalEmptyResult = Optional.empty();
        when(repository.findById(any())).thenReturn(optionalEmptyResult);

        assertThrows(NoResultException.class, () -> service.findById(id));
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(result);

        ContactDTO personSaved = service.save(dto);

        assertNotNull(personSaved);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnExceptionWhenSave() {
        when(repository.save(any())).thenThrow(new RuntimeException(MSG_INTERNAL_ERROR));

        assertThrows(BusinessException.class, () -> service.save(dto));
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnSuccessWhenUpdate() {
        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any())).thenReturn(result);

        ContactDTO contactEdited = service.update(dto);

        assertNotNull(contactEdited);
        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnNoResultExceptionWhenUpdate() {
        when(repository.existsById(any())).thenReturn(false);

        assertThrows(NoResultException.class, () -> service.update(dto));
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
        assertThrows(NoResultException.class, () -> service.delete(id));
        verify(repository, times(1)).existsById(any());
    }
}
