package br.gov.servidor.modules.servidor.dtos;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LotacaoFiltroParams {

    @QueryParam("portaria")
    private String portaria;

    @QueryParam("dataLotacao")
    private LocalDate dataLotacao;

    @QueryParam("dataRemocao")
    private LocalDate dataRemocao;
}