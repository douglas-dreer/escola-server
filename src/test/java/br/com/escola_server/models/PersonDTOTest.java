package br.com.escola_server.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonDTOTest {
    private final String MSG_NOT_EQUALS = "%s not equals";
    private final String MSG_NOT_NULL = "%s not be null";

    private final UUID id = UUID.randomUUID();
    private final String firstName = "John Doe";
    private final String lastName = "Doe";
    private final LocalDate birthday = LocalDate.now().minusYears(18);
    private final LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @Test
    public void mustReturnSuccessWhenConstructorWithoutParameters() {
        PersonDTO dto = new PersonDTO();
        assertNotNull(dto);
    }

    @Test
    public void mustReturnSuccessWhenConstructorAllWithParameters() {
        PersonDTO dto = new PersonDTO(id, firstName, lastName, birthday, createdAt, updatedAt);
        checkAll(dto);
    }

    @Test
    public void mustReturnSuccessWhenSetter() {
        PersonDTO dto = new PersonDTO();
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setBirthDate(birthday);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        checkAll(dto);
    }

    @Test
    public void mustReturnSuccessWhenBuilder() {
        PersonDTO dto = PersonDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthday)
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
            assertEquals(createdAt, dto.getCreatedAt(), createMessage("Created At", MSG_NOT_EQUALS));
            assertEquals(updatedAt, dto.getUpdatedAt(), createMessage("Updated At", MSG_NOT_EQUALS));
        });
    }

    private String createMessage(String field, String message) {
        return String.format(message, field);
    }
}
