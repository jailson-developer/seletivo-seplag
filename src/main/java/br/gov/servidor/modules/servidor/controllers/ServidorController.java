package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.modules.security.utils.Roles;
import br.gov.servidor.modules.servidor.services.ServidorService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.util.List;

@Path("/v1/servidores")
@Tag(name = "Servidores", description = "Endpoint(s) comuns aos servidores")
public class ServidorController {

    @Inject
    ServidorService service;

    @POST
    @Path("/{servidorId}/fotos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    //@APIResponse(content = {@Content(schema = @Schema(implementation = IdDto.class))}, responseCode = "201")
    // @RolesAllowed({Roles.ESCRITA})
    public Response uploadFotos(@PathParam("servidorId") Long pessoaId, @RestForm("fotos") List<FileUpload> fotos) throws IOException {
        service.uploadFotos(pessoaId, fotos);
        return Response.created(null).build();
    }

}
