package br.com.escola_server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusOperationType {
    OK("SUCCESS", "Operation executed successfully"),
    ERROR("ERROR", "The operation could not be performed."),
    NOT_FOUND("NOT_FOUND", "The operation could found results."),
    BAD_REQUEST("BAD_REQUEST", "The operation could not be performed."),
    INFO("INFORMATION", "Information"),
    WARNING("WARNING", "Warning");

    private final String code;
    private final String text;
}
