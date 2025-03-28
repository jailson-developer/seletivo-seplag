package br.gov.servidor.modules.security.controllers;


import br.gov.servidor.core.exceptions.RegraNegocioException;
import br.gov.servidor.modules.security.dtos.AuthRequestDto;
import br.gov.servidor.modules.security.dtos.AuthResponseDto;
import br.gov.servidor.modules.security.services.AuthenticationService;
import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/autenticacao")
@Tag(name = "Autenticação", description = "Endpoint(s) relacionado(s) a Autenticação")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController {

    @Inject
    AuthenticationService service;

    @SneakyThrows
    @PermitAll
    @Path("/login")
    @POST
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return service.login(request.getUsername(), request.getPassword());
    }


    @Path("/refresh")
    @PUT
    @Authenticated
    @Operation(description = "Passar o Token de refresh no Header. Ex: Authorization: Bearer eyJ0eXAiOiJK...")
    public AuthResponseDto refresh(@Context SecurityContext securityContext) {
        var user = securityContext.getUserPrincipal();
        if (user != null) {
            DefaultJWTCallerPrincipal jwtCallerPrincipal = (DefaultJWTCallerPrincipal) user;
            if (!"Refresh".equals(jwtCallerPrincipal.getClaim("type")))
                throw new RegraNegocioException("Token de refresh inválido");
            return service.refreshToken(Long.valueOf(jwtCallerPrincipal.getClaim("userId").toString()));
        }
        return null;
    }
}
