package br.gov.servidor.core.exceptions;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.ForbiddenException;

import jakarta.ws.rs.NotAcceptableException;

import jakarta.ws.rs.ServiceUnavailableException;


public class ExceptionMappers {

    @ServerExceptionMapper(BadRequestException.class)
    public Response handleBadRequestException(BadRequestException exception) {
        return Response.status(Status.BAD_REQUEST)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }

    @ServerExceptionMapper(RegraNegocioException.class)
    public Response handleRegraNegocioException(RegraNegocioException exception) {
        return Response.status(422)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }

    @ServerExceptionMapper(NotFoundException.class)
    public Response handleNotFoundException(NotFoundException exception) {
        return Response.status(Status.NOT_FOUND)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }

    @ServerExceptionMapper(InternalServerErrorException.class)
    public Response handleInternalServerErrorException(InternalServerErrorException exception) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }

    @ServerExceptionMapper(ForbiddenException.class)
    public Response handleForbiddenException(ForbiddenException exception) {
        return Response.status(Status.FORBIDDEN)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }

    @ServerExceptionMapper(UnauthorizedException.class)
    public Response handleUnauthorizedException(UnauthorizedException exception) {
        return Response.status(Status.UNAUTHORIZED)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }


    @ServerExceptionMapper(NotAcceptableException.class)
    public Response handleNotAcceptableException(NotAcceptableException exception) {
        return Response.status(Status.NOT_ACCEPTABLE)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }


    @ServerExceptionMapper(ServiceUnavailableException.class)
    public Response handleServiceUnavailableException(ServiceUnavailableException exception) {
        return Response.status(Status.SERVICE_UNAVAILABLE)
                .entity(new ResponseExceptionDto(exception.getMessage(), exception.getMessage()))
                .build();
    }
}
