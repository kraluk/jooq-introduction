package io.kraluk.playground.jooq.business;

import org.jooq.DSLContext;
import org.jooq.Record2;

import java.util.Map;

import static io.kraluk.playground.jooq.db.playground.tables.FilmActor.FILM_ACTOR;
import static java.util.stream.Collectors.toMap;
import static org.jooq.impl.DSL.count;

/**
 * {@link io.kraluk.playground.jooq.db.playground.tables.Actor} repository
 *
 * @author lukasz
 */
public class ActorRepository {

    private final DSLContext context;

    ActorRepository(final DSLContext context) {
        this.context = context;
    }

    public Map<Integer, Integer> countMoviesByActorId() {
        return context
            .select(
                FILM_ACTOR.ACTOR_ID,
                count())
            .from(FILM_ACTOR)
            .groupBy(
                FILM_ACTOR.ACTOR_ID)
            .fetch()
            .stream()
            .collect(toMap(
                Record2::component1,
                Record2::component2
            ));
    }
}
