package br.gov.servidor.core;

import io.quarkus.vertx.http.runtime.filters.QuarkusRequestWrapper;
import io.vertx.ext.web.Router;
import jakarta.enterprise.event.Observes;
import org.slf4j.MDC;

public class AppLifecycle {

    void init(@Observes Router event) {
        event.get().handler(ctx -> {
            QuarkusRequestWrapper.get(ctx.request()).addRequestDoneHandler(unused -> {
                MDC.clear();
            });
            ctx.next();
        });
    }
}
