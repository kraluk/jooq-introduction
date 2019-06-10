package io.kraluk.playground.jooq.business;

import io.kraluk.playground.jooq.db.playground.Routines;
import io.kraluk.playground.jooq.db.playground.routines.ShowLatestActor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectJoinStep;

import java.util.Map;

import static io.kraluk.playground.jooq.db.playground.Tables.ACTOR;
import static io.kraluk.playground.jooq.db.playground.tables.FilmActor.FILM_ACTOR;
import static org.jooq.impl.DSL.concat;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.max;
import static org.jooq.impl.DSL.val;

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
						count().as("FILM_COUNT"))
				.from(FILM_ACTOR)
				.groupBy(
						FILM_ACTOR.ACTOR_ID)
				.fetchMap(
						FILM_ACTOR.ACTOR_ID,
						field("FILM_COUNT", Integer.class)
				);
	}

	public String findLatestActorViaProcedureDirectly() {
		final ShowLatestActor procedure = new ShowLatestActor();

		procedure.execute(context.configuration());
		return procedure.getResults().stream().findFirst()
				.map(r -> r.get(0))
				.map(String::valueOf)
				.orElseThrow(() -> new IllegalStateException("Unable to find the latest actor!"));
	}

	public String findLatestActorViaRoutines() {
		return Routines.showLatestActor(context.configuration());
	}

	public String findLatestActor() {
		return context
				.select(concat(
						ACTOR.FIRST_NAME,
						val(" "),
						ACTOR.LAST_NAME))
				.from(ACTOR)
				.where(
						ACTOR.ACTOR_ID.eq(findLatestActorId())
				).fetchOneInto(String.class);
	}

	private SelectJoinStep<Record1<Long>> findLatestActorId() {
		return context
				.select(
						max(ACTOR.ACTOR_ID))
				.from(ACTOR);
	}
}
