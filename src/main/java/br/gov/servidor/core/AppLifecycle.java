package br.gov.servidor.core;

import br.gov.servidor.core.s3.MinioService;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import io.quarkus.vertx.http.runtime.filters.QuarkusRequestWrapper;
import io.vertx.ext.web.Router;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.MDC;

public class AppLifecycle {

    @Inject
    MinioService minioService;

    @ConfigProperty(name = "minio.bucket-foto-servidor")
    String bucketFotoServidor;

    void init(@Observes Router event) {
        event.get().handler(ctx -> {
            QuarkusRequestWrapper.get(ctx.request()).addRequestDoneHandler(unused -> {
                MDC.clear();
            });
            ctx.next();
        });
    }

    void onStart(@Observes StartupEvent event) {
        if (!ConfigUtils.isProfileActive("test")) {
            minioService.prepararBucket(bucketFotoServidor);
        }
    }
}
