package br.com.escola_server.models;

import br.com.escola_server.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDTO {
    private UUID id;
    private AddressType addressType;
    private String street;
    private int number;
    private String complement;
    private String neighborhood;
    private String postalCode;
    private String city;
    private String state;
    private boolean isMain = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
