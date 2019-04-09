package io.kraluk.playground.jooq.core.repository;

import org.jooq.UpdatableRecord;

import java.util.Collection;
import java.util.Optional;

/**
 * Simple CRUD Repository interface based on the jOOQ's {@link UpdatableRecord} implementation
 *
 * @param <PK> Primary Key type
 * @param <R>  Record type
 * @author lukasz
 */
public interface RecordRepository<PK, R extends UpdatableRecord<R>> {

    R save(final R record);

    Optional<R> find(final PK id);

    /**
     * @apiNote please be aware that these records are "attached" to the Configuration that created them.
     * This means that they hold an internal reference to the same database connection that was used to fetch them.
     * This connection is used internally by the given {@link UpdatableRecord}
     */
    Collection<R> findAll();

    R update(final R record);

    int delete(final R record);
}
