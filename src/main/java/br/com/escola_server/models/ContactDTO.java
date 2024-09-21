package br.com.escola_server.models;

import br.com.escola_server.enums.ContactType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContactDTO {
    private UUID id;
    private ContactType contactType;
    private String description;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isMain;
}
