package br.com.escola_server.entities;

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

    private String code;
    private String text;
}
