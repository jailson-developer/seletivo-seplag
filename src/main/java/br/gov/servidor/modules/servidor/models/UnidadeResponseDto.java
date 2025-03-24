package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.dtos.EnderecoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Unidade}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeResponseDto implements Serializable {
    private Long id;
    private String nome;
    private String sigla;
    private EnderecoResponseDto endereco;
}