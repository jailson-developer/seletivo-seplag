package br.gov.servidor.modules.servidor.dtos;

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
public class LotacaoRequestDto implements Serializable {
    private Long pessoaId;
    private LocalDate dataLotacao;
    private String portaria;
    private Long unidadeId;
}