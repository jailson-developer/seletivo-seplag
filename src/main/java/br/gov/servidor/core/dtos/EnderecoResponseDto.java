package br.gov.servidor.core.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link br.gov.servidor.core.models.Endereco}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoResponseDto implements Serializable {
    private Long id;
    @Size(max = 50)
    private String tipoLogradouro;
    @Size(max = 200)
    private String logradouro;
    @Size(max = 10)
    private String numero;
    @Size(max = 100)
    private String bairro;
    private CidadeDto cidade;
}