package br.gov.servidor.modules.servidor.dtos;

import br.gov.servidor.core.dtos.EnderecoRequestDto;
import br.gov.servidor.modules.servidor.enums.ESexo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.ServidorTemporario}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServidorTemporarioRequestDto implements Serializable {

    @Schema(description = "Nome completo do servidor", examples = "João da Silva")
    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    @Size(max = 200, message = "O nome não pode ter mais de 200 caracteres")
    private String nome;

    @Schema(description = "Data de nascimento do servidor", examples = "1985-06-15")
    private LocalDate dataNascimento;

    @Schema(description = "Sexo do servidor")
    @NotNull(message = "O sexo não pode ser vazio ou nulo")
    private ESexo sexo;

    @Schema(description = "Nome da mãe do servidor", examples = "Maria da Silva")
    @Size(max = 200, message = "O nome da mãe não pode ter mais de 200 caracteres")
    private String mae;

    @Schema(description = "Nome do pai do servidor", examples = "Carlos da Silva")
    @Size(max = 200, message = "O nome do pai não pode ter mais de 200 caracteres")
    private String pai;

    @Schema(description = "Data de admissão do servidor", examples = "2022-06-15")
    @NotNull(message = "A data de admissão não pode ser vazia ou nula")
    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

    @Valid
    private EnderecoRequestDto endereco;
}