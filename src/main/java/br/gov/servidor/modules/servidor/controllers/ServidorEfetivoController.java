package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResumoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEnderecoFuncionalDto;
import br.gov.servidor.modules.servidor.services.ServicorEfetivoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/v1/servidores-efetivos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Servidores Efetivos", description = "Endpoint(s) relacionado(s) aos servidores efetivos")
public class ServidorEfetivoController {

    @Inject
    ServicorEfetivoService service;

    @POST
    @Operation(summary = "Salva um novo servidor", description = "Realiza o cadastro de um novo servidor efetivo.")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @APIResponse(responseCode = "400", description = "Erro na validação de dados")
    })
    @RolesAllowed("manter_servidor")
    public Response salvar(ServidorEfetivoRequestDto servidorEfetivoRequestDto) {
        return Response.status(Response.Status.CREATED).entity(service.salvar(servidorEfetivoRequestDto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("manter_servidor")
    @Operation(summary = "Atualizar dados de um servidor", description = "Atualiza as informações de um servidor já cadastrado.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Servidor atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Servidor não encontrado"),
        @APIResponse(responseCode = "400", description = "Erro na validação de dados")
    })
    public Response atualizar(@PathParam("id") Long id, ServidorEfetivoRequestDto servidorEfetivoRequestDto) {
        return Response.ok().entity(service.atualizar(id, servidorEfetivoRequestDto)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir um servidor", description = "Exclui um servidor do sistema com base no ID.")
    @APIResponses(value = {
        @APIResponse(responseCode = "204", description = "Servidor excluído com sucesso"),
        @APIResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    @RolesAllowed("manter_servidor")
    public Response excluir(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Consultar um servidor", description = "Consulta todos os dados completos de um servidor pelo ID.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Servidor encontrado e retornado"),
        @APIResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    @RolesAllowed({"leitura_servidor", "manter_servidor"})
    public Response consultaCompleta(@PathParam("id") Long id) {
        ServidorEfetivoResponseDto responseDto = service.consultaCompleta(id);
        return Response.ok(responseDto).build();
    }

    @GET
    @Path("/por-unidade/{unidadeId}")
    @Operation(summary = "Buscar Servidores Efetivos da Unidade", description = "Busca os servidores lotados na Unidade informada")
    @RolesAllowed({"leitura_servidor", "manter_servidor"})
    public PagedResponse<ServidorEfetivoResumoResponseDto> servidoresPorUnidade(@PathParam("unidadeId") Long unidadeId, @BeanParam PageRequest pageRequest) {
        return service.servidoresPorUnidade(unidadeId, pageRequest);
    }


    @GET
    @Path("/endereco-funcional/{nomeServidor}")
    @Operation(summary = "Buscar Servidores Efetivos da Unidade", description = "Busca os servidores lotados na Unidade informada")
    @RolesAllowed({"leitura_servidor", "manter_servidor"})
    public PagedResponse<ServidorEnderecoFuncionalDto> enderecoFuncional(@PathParam("nomeServidor") String nomeServidor, @BeanParam PageRequest pageRequest) {
        return service.enderecoFuncional(nomeServidor, pageRequest);
    }

}
