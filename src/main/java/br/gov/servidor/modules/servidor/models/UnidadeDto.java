package br.gov.servidor.modules.servidor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Unidade}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeDto implements Serializable {
    private String nome;
    private String sigla;
}