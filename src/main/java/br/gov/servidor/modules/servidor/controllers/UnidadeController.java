package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.models.UnidadeRequestDto;
import br.gov.servidor.modules.servidor.models.UnidadeResponseDto;
import br.gov.servidor.modules.servidor.services.UnidadeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/v1/unidades")
@Tag(name = "Unidades", description = "Endpoint(s) de manipulação de Unidades")
public class UnidadeController {

    @Inject
    UnidadeService service;

    @POST
    @RolesAllowed("manter_unidade")
    @Operation(summary = "Salvar uma nova unidade", description = "Cria uma nova unidade")
    public Response salvar(@Valid @RequestBody UnidadeRequestDto unidadeRequestDto) {
        return Response.created(null).entity(service.salvar(unidadeRequestDto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("manter_unidade")
    @Operation(summary = "Atualizar dados de uma unidade", description = "Atualiza as informações de uma unidade já cadastrada.")
    public Response atualizar(@PathParam("id") Long id, @Valid @RequestBody UnidadeRequestDto unidadeRequestDto) {
        return Response.ok().entity(service.editar(id, unidadeRequestDto)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manter_unidade")
    @Operation(summary = "Excluir uma unidade", description = "Exclui uma unidade do sistema com base no ID.")
    public Response excluir(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Consultar uma unidade", description = "Consulta todos os dados completos de uma unidade pelo ID.")
    @RolesAllowed({"leitura_unidade", "manter_unidade"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Buscar Unidades", description = "Busca as unidades de acordo com o filtro informado")
    @RolesAllowed({"leitura_unidade", "manter_unidade"})
    public PagedResponse<UnidadeResponseDto> findAll(@BeanParam PageRequest pageRequest, @QueryParam("nome") String nome, @QueryParam("sigla") String sigla) {
        return service.findByNomeOuSigla(pageRequest, nome, sigla);
    }
}
