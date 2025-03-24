package br.gov.servidor.modules.servidor.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link br.gov.servidor.modules.servidor.models.ServidorEfetivo}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorEfetivoResumoResponseDto implements Serializable {
    private String nome;
    private Integer idade;
    private LotacaoResponseDto lotacao;
    private List<FotoResponseDto> fotos = new ArrayList<>();
}