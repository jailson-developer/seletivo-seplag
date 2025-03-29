package br.gov.servidor.modules.servidor.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.Lotacao}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotacaoRequestDto implements Serializable {
    @NotNull(message = "Servidor deve ser informado")
    @Schema(description = "Servidor", examples = "1")
    private Long pessoaId;

    @NotNull(message = "Unidade deve ser informada")
    @Schema(description = "Unidade", examples = "1")
    private Long unidadeId;

    @NotNull(message = "Data de Lotação deve ser informada")
    @Schema(description = "Data de Lotação", examples = "2022-06-15")
    private LocalDate dataLotacao;

    @Schema(description = "Data de Remocao")
    private LocalDate dataRemocao;

    @NotNull(message = "Portaria deve ser informada")
    @Schema(description = "Portaria", examples = "123456789")
    private String portaria;
}