package br.com.escola_server.services;

import br.com.escola_server.entities.Address;
import br.com.escola_server.exceptions.BusinessException;
import br.com.escola_server.models.AddressDTO;
import br.com.escola_server.repositories.AddressRepository;
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
public class AddressServiceTest extends DTOGenerator {
    private final List<Address> resultList = new ArrayList<>();
    private final String MSG_INTERNAL_ERROR = "There was an internal error. Please try again later.";
    @Mock
    private AddressRepository repository;
    @InjectMocks
    private AddressServiceImpl service;
    private UUID id;
    private Address result = new Address();
    private AddressDTO dto;

    @BeforeEach
    void setUp() {
        result = createAddress();
        id = result.getId();
        resultList.add(result);
        dto = Converter.convertTo(result, AddressDTO.class);
    }

    @Test
    void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(resultList);

        List<AddressDTO> contactResultList = service.findAll();

        assertNotNull(contactResultList);
        assertFalse(contactResultList.isEmpty());

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
        Optional<Address> optionalResult = Optional.of(result);
        when(repository.findById(any())).thenReturn(optionalResult);

        AddressDTO contactResult = service.findById(id);

        assertNotNull(contactResult);
        assertEquals(id, contactResult.getId());

        verify(repository, times(1)).findById(any());
    }

    @Test
    void mustReturnNoResultWhenFindById() {
        Optional<Address> optionalEmptyResult = Optional.empty();
        when(repository.findById(any())).thenReturn(optionalEmptyResult);

        assertThrows(NoResultException.class, () -> service.findById(id));
        verify(repository, times(1)).findById(any());
    }

    @Test
    void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(result);

        AddressDTO personSaved = service.save(dto);

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

        AddressDTO contactEdited = service.update(dto);

        assertNotNull(contactEdited);
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
