package br.gov.servidor.core.models;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * DTO for {@link Endereco}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequestDto implements Serializable {

    @Schema(description = "Tipo de logradouro", examples = "Rua")
    @Size(max = 50, message = "O tipo de logradouro não pode ter mais de 50 caracteres")
    private String tipoLogradouro;

    @Schema(description = "Nome do logradouro", examples = "Avenida Brasil")
    @Size(max = 200, message = "O logradouro não pode ter mais de 200 caracteres")
    private String logradouro;

    @Schema(description = "Número do imóvel", examples = "123")
    @Size(max = 10, message = "O número não pode ter mais de 10 caracteres")
    private String numero;

    @Schema(description = "Nome do bairro", examples = "Centro")
    @Size(max = 100, message = "O bairro não pode ter mais de 100 caracteres")
    private String bairro;

    @Schema(description = "ID da cidade", examples = "1")
    private Long cidadeId;
}
