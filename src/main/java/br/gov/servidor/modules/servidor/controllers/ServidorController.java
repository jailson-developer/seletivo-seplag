package br.gov.servidor.modules.servidor.controllers;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.ServidorEnderecoFuncionalDto;
import br.gov.servidor.modules.servidor.services.ServidorService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.util.List;

@Path("/v1/servidores")
@Tag(name = "Servidores", description = "Endpoint(s) comuns aos servidores")
public class ServidorController {

    @Inject
    ServidorService service;

    @POST
    @Path("/{servidorId}/fotos")
    @RolesAllowed("manter_servidor")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFotos(@PathParam("servidorId") Long pessoaId, @RestForm("fotos") List<FileUpload> fotos) throws IOException {
        service.uploadFotos(pessoaId, fotos);
        return Response.created(null).build();
    }
}
