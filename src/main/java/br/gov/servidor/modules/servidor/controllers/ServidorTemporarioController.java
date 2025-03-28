package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioFiltroParams;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioResumoResponseDto;
import br.gov.servidor.modules.servidor.services.ServidorTemporarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/v1/servidores-temporarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Servidores Temporários", description = "Endpoint(s) relacionado(s) aos servidores temporários")
public class ServidorTemporarioController {

    @Inject
    ServidorTemporarioService service;

    @POST
    @Operation(summary = "Salva um novo servidor", description = "Realiza o cadastro de um novo servidor temporário.")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Servidor criado com sucesso", content = @Content(schema = @Schema(implementation = ServidorTemporarioResponseDto.class))),
        @APIResponse(responseCode = "400", description = "Erro na validação de dados")
    })
    @RolesAllowed("manter_servidor")
    public Response salvar(ServidorTemporarioRequestDto temporarioRequestDto) {
        return Response.status(Response.Status.CREATED).entity(service.salvar(temporarioRequestDto)).build();
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
    public Response atualizar(@PathParam("id") Long id, ServidorTemporarioRequestDto servidorTemporarioRequestDto) {
        return Response.ok().entity(service.atualizar(id, servidorTemporarioRequestDto)).build();
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
        ServidorTemporarioResponseDto responseDto = service.consultaCompleta(id);
        return Response.ok(responseDto).build();
    }


    @GET
    @Path("/")
    @Operation(summary = "Consultar de servidores", description = "Consulta resumida de servidores")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Servidor encontrado e retornado"),
            @APIResponse(responseCode = "404", description = "Servidor não encontrado")
    })
    @RolesAllowed({"leitura_servidor", "manter_servidor"})
    public PagedResponse<ServidorTemporarioResumoResponseDto> consultaResumida(@BeanParam ServidorTemporarioFiltroParams params, @BeanParam PageRequest pageRequest) {
        return service.consultaResumida(params, pageRequest);
    }
}
