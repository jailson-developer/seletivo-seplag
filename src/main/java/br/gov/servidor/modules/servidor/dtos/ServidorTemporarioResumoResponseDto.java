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
public class ServidorTemporarioResumoResponseDto implements Serializable {
    private String id;
    private String nome;
    private String pai;
    private String mae;
}