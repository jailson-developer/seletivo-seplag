package br.gov.servidor.modules.servidor.dtos;

import br.gov.servidor.core.dtos.CidadeDto;
import br.gov.servidor.core.dtos.EnderecoResponseDto;
import br.gov.servidor.core.models.Cidade;
import br.gov.servidor.core.models.Endereco;
import br.gov.servidor.modules.servidor.models.ServidorEfetivo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorEnderecoFuncionalDto {
    private String nome;
    private String mae;
    private String pai;
    private EnderecoResponseDto enderecoFuncional;

    public ServidorEnderecoFuncionalDto(Endereco endereco, ServidorEfetivo servidorEfetivo, Cidade cidade) {
        this.nome = servidorEfetivo.getNome();
        this.mae = servidorEfetivo.getMae();
        this.pai = servidorEfetivo.getPai();
        this.enderecoFuncional = EnderecoResponseDto.builder()
                .cidade(CidadeDto.builder()
                        .nome(cidade.getNome())
                        .uf(cidade.getUf())
                        .build())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .tipoLogradouro(endereco.getTipoLogradouro())
                .id(endereco.getId())
                .build();
    }
}