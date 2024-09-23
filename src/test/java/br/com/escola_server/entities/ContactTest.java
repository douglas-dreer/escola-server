package br.com.escola_server.entities;

import br.com.escola_server.enums.ContactType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
 public class ContactTest {
    private final String MSG_NOT_EQUALS = "%s not equals";
    private final String MSG_NOT_NULL = "%s not be null";

    private final UUID id = UUID.randomUUID();
    private final ContactType contactType = ContactType.MOBILE;
    private final String value = "99999999999";
    private final String notes = "Notes to contact";
    private final boolean isMain = true;
    private final LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @Test
     void mustReturnSuccessWhenConstructorWithoutParameters() {
        Contact entity = new Contact();
        assertNotNull(entity);
    }

    @Test
     void mustReturnSuccessWhenConstructorAllWithParameters() {
        Contact entity = new Contact(id, contactType, value, notes, createdAt, updatedAt, isMain);
        checkAll(entity);
    }

    @Test
     void mustReturnSuccessWhenSetter() {
        Contact entity = new Contact();
        entity.setId(id);
        entity.setContactType(contactType);
        entity.setValue(value);
        entity.setNotes(notes);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        entity.setMain(isMain);
        checkAll(entity);
    }

    @Test
     void mustReturnSuccessWhenBuilder() {
        Contact entity = Contact.builder()
                .id(id)
                .contactType(contactType)
                .value(value)
                .notes(notes)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isMain(isMain)
                .build();
        checkAll(entity);
    }

    private void checkAll(Contact entity) {
        assertNotNull(entity);
        assertAll(() -> {
            assertNotNull(entity, createMessage("Contact", MSG_NOT_NULL));

            assertNotNull(entity.getId());
            assertEquals(id, entity.getId());

            assertEquals(contactType, entity.getContactType(), createMessage("Contact Type", MSG_NOT_EQUALS));
            assertEquals(value, entity.getValue(), createMessage("value", MSG_NOT_EQUALS));
            assertEquals(notes, entity.getNotes(), createMessage("notes", MSG_NOT_EQUALS));
            assertEquals(createdAt, entity.getCreatedAt(), createMessage("Created At", MSG_NOT_EQUALS));
            assertEquals(updatedAt, entity.getUpdatedAt(), createMessage("Updated At", MSG_NOT_EQUALS));
            assertEquals(isMain, entity.isMain(), createMessage("Is Main", MSG_NOT_EQUALS));
        });
    }

    private String createMessage(String field, String message) {
        return String.format(message, field);
    }
}
