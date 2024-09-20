package br.com.escola_server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContactType {
    WHATS_APP("WhatsApp"),
    EMAIL("E-mail"),
    MOBILE("Celular");

    private final String description;
}
