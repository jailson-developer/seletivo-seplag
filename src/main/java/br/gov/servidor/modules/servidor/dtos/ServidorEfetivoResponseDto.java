package br.gov.servidor.modules.servidor.dtos;

import br.gov.servidor.core.dtos.EnderecoResponseDto;
import br.gov.servidor.modules.servidor.enums.ESexo;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.ServidorEfetivo}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorEfetivoResponseDto implements Serializable {

    private Long id;
    @Size(max = 200)
    private String nome;
    private LocalDate dataNascimento;
    private ESexo sexo;
    @Size(max = 200)
    private String mae;
    @Size(max = 200)
    private String pai;
 //   private Set<LotacaoResponseDto> lotacoes = new LinkedHashSet<>();
    private EnderecoResponseDto endereco;
    private String matricula;
    private List<FotoResponseDto> fotos = new ArrayList<>();
}