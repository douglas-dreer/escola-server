package br.com.escola_server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDTO {
    private UUID id;
    private String description;
    private Int type;
}
