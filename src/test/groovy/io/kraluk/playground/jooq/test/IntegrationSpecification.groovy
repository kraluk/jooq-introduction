package io.kraluk.playground.jooq.test

import io.kraluk.playground.jooq.JooqPlaygroundApp
import ratpack.impose.ImpositionsSpec
import ratpack.impose.UserRegistryImposition
import ratpack.registry.Registry
import ratpack.test.MainClassApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Specification

class IntegrationSpecification extends Specification {

    @AutoCleanup
    MainClassApplicationUnderTest aut = new MainClassApplicationUnderTest(JooqPlaygroundApp.class) {
        @Override
        protected void addImpositions(final ImpositionsSpec impositions) {
            impositions.add(UserRegistryImposition.of {
                registry ->
                    appRegistry = registry
                    return registry
            })
        }
    }

    Registry appRegistry

    def <T> T inject(final Class<T> classOf) {
        aut.getAddress()
        return appRegistry.get(classOf)
    }
}
