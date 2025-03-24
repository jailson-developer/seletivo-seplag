package br.gov.servidor.core.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.query.sqm.produce.function.FunctionParameterType;
import org.hibernate.type.StandardBasicTypes;

public class CustomPostgreSQLDialect extends PostgresPlusDialect {

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        var stringType = functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(StandardBasicTypes.STRING);
        functionContributions.getFunctionRegistry().namedDescriptorBuilder("unaccent")
                .setInvariantType(stringType)
                .setExactArgumentCount(1)
                .setParameterTypes(FunctionParameterType.STRING)
                .setArgumentListSignature("(STRING string)")
                .register();

        functionContributions.getFunctionRegistry().registerPattern(
                "fts", "(?1) @@ plainto_tsquery('pt_br', unaccent(?2))",
                functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(StandardBasicTypes.BOOLEAN)
        );
    }
}