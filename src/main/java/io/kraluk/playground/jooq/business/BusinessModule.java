package io.kraluk.playground.jooq.business;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Domain Module configuration
 *
 * @author lukasz
 */
public class BusinessModule extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(BusinessModule.class);

    @Override
    protected void configure() {
        log.info("Business module configured.");
    }

    @Provides
    @Singleton
    FilmRepository filmRepository(final DSLContext context) {
        return new FilmRepository(context);
    }

    @Provides
    @Singleton
    FilmRecordRepository filmRecordRepository(final DSLContext context) {
        return new FilmRecordRepository(context);
    }

    @Provides
    @Singleton
    ActorRepository actorRepository(final DSLContext context) {
        return new ActorRepository(context);
    }
}
