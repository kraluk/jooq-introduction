package io.kraluk.playground.jooq.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.ZoneId;

/**
 * Core Module of the JooqPlaygroundApp - provides default tooling
 *
 * @author lukasz
 */
public class CoreModule extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(CoreModule.class);

    @Override
    protected void configure() {
        log.info("Core Module configured.");
    }

    @Provides
    @Singleton
    Clock clock() {
        return Clock.system(ZoneId.systemDefault());
    }

    @Provides
    @Singleton
    ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
    }
}
