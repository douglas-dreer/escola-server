package br.com.escola_server.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonDTOTest {
    private static final String MSG_NOT_EQUALS = "%s not equals";
    private static final String MSG_NOT_NULL = "%s not be null";
    private static final String MSG_NOT_EMPTY = "%s not be empty";

    private static final UUID id = UUID.randomUUID();
    private static final String firstName = "John Doe";
    private static final String lastName = "Doe";
    private static final List<ContactDTO> contacts = new ArrayList<>();
    private static final LocalDate birthday = LocalDate.now().minusYears(18);
    private static final LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime updatedAt = LocalDateTime.now();

    @BeforeAll
    public static void setup() {
        contacts.add(new ContactDTO());
    }

    @Test
    void mustReturnSuccessWhenConstructorWithoutParameters() {
        PersonDTO dto = new PersonDTO();
        assertNotNull(dto);
    }

    @Test
    void mustReturnSuccessWhenConstructorAllWithParameters() {
        PersonDTO dto = new PersonDTO(id, firstName, lastName, birthday, contacts, createdAt, updatedAt);
        checkAll(dto);
    }

    @Test
    void mustReturnSuccessWhenSetter() {
        PersonDTO dto = new PersonDTO();
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setBirthDate(birthday);
        dto.setContacts(contacts);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        checkAll(dto);
    }

    @Test
    void mustReturnSuccessWhenBuilder() {
        PersonDTO dto = PersonDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthday)
                .contacts(contacts)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        checkAll(dto);
    }

    private void checkAll(PersonDTO dto) {
        assertNotNull(dto);
        assertAll(() -> {
            assertNotNull(dto, createMessage("PersonDTO", MSG_NOT_NULL));

            assertNotNull(dto.getId());
            assertEquals(id, dto.getId());

            assertEquals(firstName, dto.getFirstName(), createMessage("First Name", MSG_NOT_EQUALS));
            assertEquals(lastName, dto.getLastName(), createMessage("Last Name", MSG_NOT_EQUALS));
            assertEquals(birthday, dto.getBirthDate(), createMessage("Birthday", MSG_NOT_EQUALS));
            assertFalse(contacts.isEmpty(), createMessage("Contacts", MSG_NOT_EMPTY));
            assertEquals(createdAt, dto.getCreatedAt(), createMessage("Created At", MSG_NOT_EQUALS));
            assertEquals(updatedAt, dto.getUpdatedAt(), createMessage("Updated At", MSG_NOT_EQUALS));
        });
    }

    private String createMessage(String field, String message) {
        return String.format(message, field);
    }
}
