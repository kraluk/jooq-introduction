package io.kraluk.playground.jooq.business;

import io.kraluk.playground.jooq.core.repository.Repository;
import io.kraluk.playground.jooq.db.playground.tables.pojos.Actor;
import io.kraluk.playground.jooq.db.playground.tables.pojos.Film;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.kraluk.playground.jooq.db.playground.Tables.ACTOR;
import static io.kraluk.playground.jooq.db.playground.Tables.FILM_ACTOR;
import static io.kraluk.playground.jooq.db.playground.tables.Film.FILM;
import static org.jooq.impl.DSL.max;

/**
 * {@link io.kraluk.playground.jooq.db.playground.tables.Film} repository
 *
 * @author lukasz
 */
public class FilmRepository implements Repository<Integer, Film> {
    private static final Logger log = LoggerFactory.getLogger(FilmRepository.class);

    private final DSLContext context;

    FilmRepository(final DSLContext context) {
        this.context = context;
    }

    @Override
    public Film save(final Film film) {
        final Film saved = context
            .insertInto(FILM)
            .set(FILM.TITLE, film.getTitle())
            .set(FILM.DESCRIPTION, film.getDescription())
            .set(FILM.RELEASE_YEAR, film.getReleaseYear())
            .set(FILM.LANGUAGE_ID, film.getLanguageId())
            .set(FILM.ORIGINAL_LANGUAGE_ID, film.getOriginalLanguageId())
            .set(FILM.RENTAL_DURATION, film.getRentalDuration())
            .set(FILM.RENTAL_RATE, film.getRentalRate())
            .set(FILM.LENGTH, film.getLength())
            .set(FILM.REPLACEMENT_COST, film.getReplacementCost())
            .set(FILM.RATING, film.getRating())
            .set(FILM.SPECIAL_FEATURES, film.getSpecialFeatures())
            .returning()
            .fetchOne()
            .into(Film.class);

        log.info("Saved '{}'", saved);
        return saved;
    }

    @Override
    public Optional<Film> find(final Integer id) {
        final Optional<Film> found = context
            .select()
            .from(FILM)
            .where(
                FILM.FILM_ID.eq(id)
            )
            .fetchOptionalInto(Film.class);

        log.info("Found '{}'", found);
        return found;
    }

    @Override
    public Collection<Film> findAll() {
        return context
            .select()
            .from(FILM)
            .fetch()
            .into(Film.class);
    }

    @Override
    public Film update(final Film film) {
        context
            .update(FILM)
            .set(FILM.TITLE, film.getTitle())
            .set(FILM.DESCRIPTION, film.getDescription())
            .set(FILM.RELEASE_YEAR, film.getReleaseYear())
            .set(FILM.LANGUAGE_ID, film.getLanguageId())
            .set(FILM.ORIGINAL_LANGUAGE_ID, film.getOriginalLanguageId())
            .set(FILM.RENTAL_DURATION, film.getRentalDuration())
            .set(FILM.RENTAL_RATE, film.getRentalRate())
            .set(FILM.LENGTH, film.getLength())
            .set(FILM.REPLACEMENT_COST, film.getReplacementCost())
            .set(FILM.RATING, film.getRating())
            .set(FILM.SPECIAL_FEATURES, film.getSpecialFeatures())
            .where(
                FILM.FILM_ID.eq(film.getFilmId())
            )
            .execute();

        final Film updated = find(film.getFilmId())
            .orElseThrow(() -> new IllegalStateException(""));

        log.info("Updated '{}'", updated);
        return updated;
    }

    @Override
    public int delete(final Integer id) {
        final int deleted = context
            .delete(FILM)
            .where(
                FILM.FILM_ID.eq(id)
            )
            .execute();

        log.info("Deleted '{}' elements with id '{}'", deleted, id);
        return deleted;
    }

    public Integer findLastId() {
        return context
            .select(max(FILM.FILM_ID))
            .from(FILM)
            .fetchOne()
            .component1();
    }

    public List<Film> findLastUpdated() {
        return context
            .select()
            .from(FILM)
            .where(
                FILM.LAST_UPDATE.eq(
                    context
                        .select(
                            max(FILM.LAST_UPDATE)
                        )
                        .from(FILM)
                        .fetchOne()
                        .component1()
                )
            )
            .fetchInto(Film.class);
    }

    public Map<String, List<Integer>> findIdsGropedByReleaseYear() {
        return context
            .select(
                FILM.RELEASE_YEAR,
                FILM.FILM_ID
            )
            .from(FILM)
            .groupBy(
                FILM.RELEASE_YEAR,
                FILM.FILM_ID
            )
            .fetchGroups(FILM.RELEASE_YEAR, FILM.FILM_ID);
    }

    public Map<Film, List<Actor>> findWithActors() {
        return context
            .select()
            .from(FILM)
            .leftJoin(FILM_ACTOR).on(FILM.FILM_ID.eq(FILM_ACTOR.FILM_ID))
            .leftJoin(ACTOR).on(ACTOR.ACTOR_ID.eq(FILM_ACTOR.ACTOR_ID))
            .orderBy(FILM.TITLE.asc())
            .limit(100) // limits before grouping!
            .fetchGroups( // == GROUP BY on the Java level
                r -> r.into(FILM).into(Film.class),
                r -> r.into(ACTOR).into(Actor.class));
    }
}
