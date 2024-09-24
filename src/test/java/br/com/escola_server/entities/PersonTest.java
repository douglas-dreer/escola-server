package br.com.escola_server.entities;

import br.com.escola_server.models.ContactDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonTest {
    private static final String MSG_NOT_EQUALS = "%s not equals";
    private static final String MSG_NOT_NULL = "%s not be null";
    private static final String MSG_NOT_EMPTY = "%s not be empty";

    private static final UUID id = UUID.randomUUID();
    private static final String firstName = "John Doe";
    private static final String lastName = "Doe";
    private static final LocalDate birthday = LocalDate.now().minusYears(18);
    private static final List<Contact> contacts = new ArrayList<>();
    private static final LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime updatedAt = LocalDateTime.now();

    @BeforeAll
    public static void setUp() {
        contacts.add(new Contact());
    }

    @Test
    void mustReturnSuccessWhenConstructorWithoutParameters() {
        Person entity = new Person();
        assertNotNull(entity);
    }

    @Test
    void mustReturnSuccessWhenConstructorAllWithParameters() {
        Person entity = new Person(id, firstName, lastName, birthday, contacts,  createdAt, updatedAt);
        checkAll(entity);
    }

    @Test
    void mustReturnSuccessWhenSetter() {
        Person entity = new Person();
        entity.setId(id);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setBirthDate(birthday);
        entity.setContacts(contacts);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        checkAll(entity);
    }

    @Test
    void mustReturnSuccessWhenBuilder() {
        Person entity = Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthday)
                .contacts(contacts)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        checkAll(entity);
    }

    private void checkAll(Person entity) {
        assertNotNull(entity);
        assertAll(() -> {
            assertNotNull(entity, createMessage("Person", MSG_NOT_NULL));

            assertNotNull(entity.getId());
            assertEquals(id, entity.getId());

            assertEquals(firstName, entity.getFirstName(), createMessage("First Name", MSG_NOT_EQUALS));
            assertEquals(lastName, entity.getLastName(), createMessage("Last Name", MSG_NOT_EQUALS));
            assertEquals(birthday, entity.getBirthDate(), createMessage("Birthday", MSG_NOT_EQUALS));
            assertFalse(contacts.isEmpty(), createMessage("Contacts", MSG_NOT_EMPTY));
            assertEquals(createdAt, entity.getCreatedAt(), createMessage("Created At", MSG_NOT_EQUALS));
            assertEquals(updatedAt, entity.getUpdatedAt(), createMessage("Updated At", MSG_NOT_EQUALS));
        });
    }

    private String createMessage(String field, String message) {
        return String.format(message, field);
    }
}
