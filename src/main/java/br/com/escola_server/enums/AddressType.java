package br.com.escola_server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressType {
    RESIDENCIAL("Residencial"),
    BUSINESS("Comercial");

    private final String description;
}
