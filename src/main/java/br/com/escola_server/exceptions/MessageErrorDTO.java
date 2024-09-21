package br.com.escola_server.exceptions;

import br.com.escola_server.entities.StatusOperationType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageErrorDTO {
    private String errorMessage;
    private String localDateTime;
    private String code;
    private String details;

    public MessageErrorDTO(String errorMessage, StatusOperationType code) {
        this.details = messageTreatment(errorMessage);
        this.localDateTime = LocalDateTime.now().toString();
        this.code = code.getCode();
        this.errorMessage = code.getText();
    }

    private String messageTreatment(String message) {
        if (message.contains("CityEnum")) {
            return "Unable to find the city.";
        }
        return message;
    }
}
