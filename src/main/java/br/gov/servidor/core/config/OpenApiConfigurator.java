package br.gov.servidor.core.config;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema;

import java.util.List;

public class OpenApiConfigurator implements OASFilter {
    @Override
    public void filterOpenAPI(OpenAPI openAPI) {
        Schema schema = OASFactory.createSchema();
        schema.setType(List.of(Schema.SchemaType.STRING));
        schema.setFormat("binary");

        openAPI.getComponents().addSchema("FileUpload", schema);
        OASFilter.super.filterOpenAPI(openAPI);
    }
}
