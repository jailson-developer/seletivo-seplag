package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResumoResponseDto;
import br.gov.servidor.modules.servidor.services.ServicorEfetivoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


@Path("/v1/servidores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServidorEfetivoController {

    @Inject
    ServicorEfetivoService servidorEfetivoService;

    @POST
    @Operation(summary = "Salvar um novo servidor", description = "Realiza o cadastro de um novo servidor efetivo.")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Servidor criado com sucesso"),
        @APIResponse(responseCode = "400", description = "Erro na validação de dados")
    })
    public Response salvar(ServidorEfetivoRequestDto servidorEfetivoRequestDto) {
        servidorEfetivoService.salvar(servidorEfetivoRequestDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar dados de um servidor", description = "Atualiza as informações de um servidor já cadastrado.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Servidor atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Servidor não encontrado"),
        @APIResponse(responseCode = "400", description = "Erro na validação de dados")
    })
    public Response atualizar(@PathParam("id") Long id, ServidorEfetivoRequestDto servidorEfetivoRequestDto) {
        servidorEfetivoService.atualizar(id, servidorEfetivoRequestDto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir um servidor", description = "Exclui um servidor do sistema com base no ID.")
    @APIResponses(value = {
        @APIResponse(responseCode = "204", description = "Servidor excluído com sucesso"),
        @APIResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public Response excluir(@PathParam("id") Long id) {
        servidorEfetivoService.excluir(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Consultar um servidor", description = "Consulta todos os dados completos de um servidor pelo ID.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Servidor encontrado e retornado"),
        @APIResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    public Response consultaCompleta(@PathParam("id") Long id) {
        ServidorEfetivoResponseDto responseDto = servidorEfetivoService.consultaCompleta(id);
        return Response.ok(responseDto).build();
    }

    @GET
    @Operation(summary = "Consultar servidores", description = "Consulta servidores efetivos com paginação.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Servidores retornados com sucesso"),
        @APIResponse(responseCode = "400", description = "Erro nos parâmetros de paginação")
    })
    public Response consulta(@BeanParam PageRequest pageRequest) {
        PagedResponse<ServidorEfetivoResumoResponseDto> response = servidorEfetivoService.consulta(pageRequest);
        return Response.ok(response).build();
    }
}
