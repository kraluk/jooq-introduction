package io.kraluk.playground.jooq.core.repository;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import ratpack.hikari.HikariModule;
import ratpack.server.ServerConfig;

/**
 * Describes Repository Module based on {@link HikariModule} and provides its initial configuration (which could/should be externalized sometime)
 *
 * @author lukasz
 */
public final class RepositoryModule extends HikariModule {
    private static final Logger log = LoggerFactory.getLogger(RepositoryModule.class);

    @Override
    protected void configure() {
        log.info("Repository module configured.");
    }

    @Override
    protected void defaultConfig(final ServerConfig serverConfig, final HikariConfig config) {
        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.addDataSourceProperty(
            "URL",
            "jdbc:h2:mem:jooqplayground;INIT=RUNSCRIPT FROM './src/main/sql/sakila-ddl.sql'\\;RUNSCRIPT FROM './src/main/sql/sakila-procedures.sql'\\;RUNSCRIPT FROM './src/main/sql/sakila-dml.sql'");
    }

    @Provides
    @Singleton
    DSLContext dslContext(final DataSource dataSource) {
        return DSL.using(
            dataSource,
            SQLDialect.H2);
    }
}
