package br.gov.servidor.modules.servidor.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.Lotacao}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LotacaoResponseDto implements Serializable {
    private LocalDate dataLotacao;
    private String portaria;
    private String unidade;
    private Long id;

}