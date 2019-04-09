package io.kraluk.playground.jooq.business;

import io.kraluk.playground.jooq.core.repository.RecordRepository;
import io.kraluk.playground.jooq.db.playground.tables.records.FilmRecord;

import org.jooq.DSLContext;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.kraluk.playground.jooq.db.playground.tables.Film.FILM;

/**
 * {@link io.kraluk.playground.jooq.db.playground.tables.Film} repository based on the {@link FilmRecord} Active Record implementation
 *
 * @author lukasz
 */
public class FilmRecordRepository implements RecordRepository<Integer, FilmRecord> {

    private final DSLContext context;

    FilmRecordRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public FilmRecord save(final FilmRecord record) {
        record.store();
        record.refresh();
        return record;
    }

    @Override
    public Optional<FilmRecord> find(final Integer id) {
        return context
            .selectFrom(FILM)
            .where(
                FILM.FILM_ID.eq(id)
            ).fetchOptional();
    }

    @Override
    public Collection<FilmRecord> findAll() {
        return context
            .select()
            .from(FILM)
            .fetch() // Eagerly load the whole ResultSet into memory first
            .stream() // Without fetch() it will create a "lazy" Cursor, that keeps an open underlying JDBC ResultSet
            .map(r -> r.into(FILM))
            .collect(Collectors.toList());
    }

    @Override
    public FilmRecord update(final FilmRecord record) {
        record.store();
        record.refresh();
        return record;
    }

    @Override
    public int delete(final FilmRecord record) {
        return record.delete();
    }
}
