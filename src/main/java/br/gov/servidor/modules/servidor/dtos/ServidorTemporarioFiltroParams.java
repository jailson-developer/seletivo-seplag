package br.gov.servidor.modules.servidor.dtos;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorTemporarioFiltroParams {

    @QueryParam("nome")
    private String nome;

    @QueryParam("mae")
    private String mae;

    @QueryParam("pai")
    private String pai;
}