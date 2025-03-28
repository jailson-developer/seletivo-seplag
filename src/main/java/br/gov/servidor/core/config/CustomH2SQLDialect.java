package br.gov.servidor.core.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.query.sqm.produce.function.FunctionParameterType;
import org.hibernate.type.StandardBasicTypes;

public class CustomH2SQLDialect extends org.hibernate.dialect.H2Dialect {

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

    }
}