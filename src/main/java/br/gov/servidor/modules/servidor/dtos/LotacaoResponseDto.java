package br.gov.servidor.modules.servidor.dtos;

import br.gov.servidor.modules.servidor.models.UnidadeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.Lotacao}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotacaoResponseDto implements Serializable {
    private Long id;
    private LocalDate dataLotacao;
    private LocalDate dataRemocao;
    private String portaria;
    private UnidadeDto unidade;
}