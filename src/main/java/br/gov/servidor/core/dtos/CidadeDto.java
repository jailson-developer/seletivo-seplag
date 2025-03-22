package br.gov.servidor.core.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * DTO for {@link br.gov.servidor.core.models.Cidade}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDto implements Serializable {

    @Size(max = 200)
    @Schema(examples = "Cuiabá")
    private String nome;

    @Size(max = 2)
    @Schema(examples = "MT")
    private String uf;
}