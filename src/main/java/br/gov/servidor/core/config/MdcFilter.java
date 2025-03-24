package br.gov.servidor.core.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logmanager.MDC;

import java.util.UUID;

@Provider
public class MdcFilter implements ContainerRequestFilter {

    public static final String X_REQUEST_ID = "X-Request-ID";

    @Override
    public void filter(ContainerRequestContext request) {
        String requestId = request.getHeaderString(X_REQUEST_ID);
        if (StringUtils.isBlank(requestId))
            requestId = UUID.randomUUID().toString();
        MDC.put(X_REQUEST_ID, requestId);
    }
}
