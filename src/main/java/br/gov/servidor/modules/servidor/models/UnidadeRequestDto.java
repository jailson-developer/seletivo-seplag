package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.dtos.EnderecoRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * DTO for {@link Unidade}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeRequestDto implements Serializable {
    @Schema(description = "Nome da unidade", examples = "DELEGACIA ESPECIALIZADA EM ROUBOS E FURTOS")
    @NotBlank(message = "O nome da unidade não pode ser vazio")
    private String nome;
    @Schema(description = "Sigla da unidade", examples = "DERF")
    @NotBlank(message = "A sigla da unidade não pode ser vazia")
    private String sigla;
    @Valid
    @NotNull(message = "Endereço da unidade não pode ser nulo")
    private EnderecoRequestDto endereco;
}