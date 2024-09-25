package br.com.escola_server.controllers;

import br.com.escola_server.models.AddressDTO;
import br.com.escola_server.services.AddressServiceImpl;
import br.com.escola_server.utilitaries.Converter;
import br.com.escola_server.utilitaries.DTOGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest extends DTOGenerator {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressServiceImpl service;

    private static final String ENDPOINT = "/addresses";
    private final List<AddressDTO> resultList = new ArrayList<>();
    private UUID id;
    private AddressDTO result = new AddressDTO();
    private String addressJSON;

    @BeforeEach
    void setup() throws JsonProcessingException {
        result = createAddressDTO();
        resultList.add(result);
        id = result.getId();
        addressJSON = Converter.toJSON(result);
    }

    @Test
    void mustReturnSuccessWhenFindAll() throws Exception {
        MockHttpServletRequestBuilder getMethod = get(ENDPOINT);

        when(service.findAll()).thenReturn(resultList);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void mustReturnSuccessWhenFindById() throws Exception {
        final String URI = String.format("%s/%s", ENDPOINT, id);
        MockHttpServletRequestBuilder getMethod = get(URI);
        when(service.findById(id)).thenReturn(result);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnSuccessWhenSave() throws Exception {
        when(service.save(result)).thenReturn(result);

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(addressJSON);

        mockMvc.perform(postMethod)
                .andExpect(status().isCreated());
    }

    @Test
    void mustReturnSuccessWhenUpdate() throws Exception {
        when(service.update(result)).thenReturn(result);

        MockHttpServletRequestBuilder patchMethod = patch(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(addressJSON);

        mockMvc.perform(patchMethod)
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnSuccessWhenDelete() throws Exception {
        doNothing().when(service).delete(any());

        final String URI = String.format("%s/%s", ENDPOINT, id);
        MockHttpServletRequestBuilder deleteMethod = delete(URI);

        mockMvc.perform(deleteMethod)
                .andExpect(status().isOk());
    }
}
