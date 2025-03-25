package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.LotacaoFiltroParams;
import br.gov.servidor.modules.servidor.dtos.LotacaoRequestDto;
import br.gov.servidor.modules.servidor.dtos.LotacaoResponseDto;
import br.gov.servidor.modules.servidor.services.LotacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/v1/lotacoes")
@Tag(name = "Lotações", description = "Endpoint(s) de manipulação de Lotações")
public class LotacaoController {

    @Inject
    LotacaoService service;

    @POST
    @RolesAllowed("manter_lotacao")
    @Operation(summary = "Salvar uma nova lotação", description = "Cria uma nova lotação")
    public Response salvar(LotacaoRequestDto lotacao) {
        return Response.created(null).entity(service.salvar(lotacao)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("manter_lotacao")
    @Operation(summary = "Atualizar dados de uma lotação", description = "Atualiza as informações de uma lotação já cadastrada.")
    public Response atualizar(@PathParam("id") Long id, LotacaoRequestDto lotacao) {
        return Response.ok().entity(service.editar(id, lotacao)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manter_lotacao")
    @Operation(summary = "Excluir uma lotação", description = "Exclui uma lotação do sistema com base no ID.")
    public Response excluir(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Consultar uma lotação", description = "Consulta todos os dados completos de uma lotação pelo ID.")
    @RolesAllowed({"leitura_lotacao", "manter_lotacao"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Buscar Lotações", description = "Busca as lotações de acordo com o filtro informado")
    @RolesAllowed({"leitura_lotacao", "manter_lotacao"})
    public PagedResponse<LotacaoResponseDto> findByParams(@BeanParam PageRequest pageRequest, @BeanParam LotacaoFiltroParams filtro) {
        return service.findByParams(pageRequest, filtro);
    }
}
