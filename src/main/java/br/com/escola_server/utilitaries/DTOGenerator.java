package br.com.escola_server.utilitaries;

import br.com.escola_server.entities.Contact;
import br.com.escola_server.entities.Person;
import br.com.escola_server.enums.ContactType;
import br.com.escola_server.models.ContactDTO;
import br.com.escola_server.models.PersonDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class DTOGenerator {
    public PersonDTO createPersonDTO() {
        ContactDTO contactDTO = createContactDTO();
        return PersonDTO
                .builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .contacts(Collections.singletonList(contactDTO))
                .birthDate(LocalDate.now().minusYears(18))
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Person createPerson() {
        return Converter.convertTo(createPersonDTO(), Person.class);
    }

    public ContactDTO createContactDTO() {
        return ContactDTO.builder()
                .id(UUID.randomUUID())
                .value("43999999999")
                .contactType(ContactType.MOBILE)
                .notes("Principal Mobile")
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .updatedAt(LocalDateTime.now())
                .isMain(true)
                .build();
    }

    public Contact createContact() {
        return Converter.convertTo(createContactDTO(), Contact.class);
    }


}
