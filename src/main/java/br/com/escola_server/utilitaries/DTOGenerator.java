package br.com.escola_server.utilitaries;

import br.com.escola_server.entities.Address;
import br.com.escola_server.entities.Contact;
import br.com.escola_server.entities.Person;
import br.com.escola_server.enums.ContactType;
import br.com.escola_server.models.AddressDTO;
import br.com.escola_server.models.ContactDTO;
import br.com.escola_server.models.PersonDTO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Slf4j
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
        PersonDTO personDTO = createPersonDTO();
        return Converter.convertTo(personDTO, Person.class);
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
        ContactDTO contactDTO = createContactDTO();
        return Converter.convertTo(contactDTO, Contact.class);
    }

    public AddressDTO createAddressDTO() {
        return AddressDTO.builder()
                .id(UUID.randomUUID())
                .street("Street")
                .number(1)
                .complement("Complement")
                .neighborhood("Neighborhood")
                .postalCode("12345678")
                .city("City")
                .state("State")
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .updatedAt(LocalDateTime.now())
                .isMain(true)
                .build();
    }

    public Address createAddress() {
        AddressDTO addressDTO = createAddressDTO();
        return Converter.convertTo(addressDTO, Address.class);
    }


}
