package io.kraluk.playground.jooq;

import io.kraluk.playground.jooq.core.CoreModule;
import io.kraluk.playground.jooq.core.repository.RepositoryModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.error.internal.DefaultDevelopmentErrorHandler;
import ratpack.exec.Promise;
import ratpack.guice.Guice;
import ratpack.handling.ResponseTimer;
import ratpack.server.RatpackServer;

/**
 * JooqPlaygroundApp main class
 *
 * @author lukasz
 */
public final class JooqPlaygroundApp {
    private static final Logger log = LoggerFactory.getLogger(JooqPlaygroundApp.class);

    public static void main(final String... args) throws Exception {
        log.info("Starting JooqPlaygroundApp...");

        RatpackServer.start(server -> server
            .registry(Guice.registry(bindings -> bindings
                // Error handlers
                .bind(DefaultDevelopmentErrorHandler.class)
                .bindInstance(ResponseTimer.decorator())

                // Technical modules
                .module(CoreModule.class)
                .module(RepositoryModule.class)
            ))
            .handlers(chain -> chain
                .get("", ctx -> ctx.byContent(m -> m
                    .json(() -> Promise.value("JooqPlaygroundApp").then(ctx.getResponse()::send))))
            )
        );
    }
}
