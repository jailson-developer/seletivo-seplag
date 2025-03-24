package br.gov.servidor.modules.servidor.dtos;

import br.gov.servidor.core.dtos.EnderecoResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorEnderecoFuncionalDto {
    private String nome;
    private String mae;
    private String pai;
    private String matricula;
    private EnderecoResponseDto endereco;
}