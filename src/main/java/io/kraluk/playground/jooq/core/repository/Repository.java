package io.kraluk.playground.jooq.core.repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Simple CRUD Repository interface
 *
 * @param <PK> Primary Key type
 * @param <E>  Entity type
 * @author lukasz
 */
public interface Repository<PK, E> {

    E save(final E entity);

    Optional<E> find(final PK id);

    Collection<E> findAll();

    E update(final E entity);

    int delete(final PK id);
}
