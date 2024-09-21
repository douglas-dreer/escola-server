package br.com.escola_server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "DOCUMENTS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Document {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "type", nullable = false)
    private Int type;

}
