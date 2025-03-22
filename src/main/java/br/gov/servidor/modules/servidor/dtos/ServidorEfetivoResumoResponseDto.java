package br.gov.servidor.modules.servidor.dtos;

import br.gov.servidor.modules.servidor.enums.ESexo;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.ServidorEfetivo}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorEfetivoResumoResponseDto implements Serializable {
    private Long id;
    @Size(max = 200)
    private String nome;
    private LocalDate dataNascimento;
    private ESexo sexo;
    @Size(max = 200)
    private String mae;
    private String matricula;
}