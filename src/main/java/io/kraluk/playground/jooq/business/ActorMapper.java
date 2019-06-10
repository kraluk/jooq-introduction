package io.kraluk.playground.jooq.business;

import io.kraluk.playground.jooq.db.playground.tables.pojos.Actor;
import io.kraluk.playground.jooq.db.playground.tables.records.ActorRecord;

import org.jooq.Record;
import org.jooq.RecordMapper;

import static io.kraluk.playground.jooq.db.playground.Tables.ACTOR;

/**
 * Simple {@link ActorRecord} to {@link Actor} mapper
 *
 * @author lukasz
 */
class ActorMapper implements RecordMapper<Record, Actor> {

    @Override
    public Actor map(final Record record) {

        return new Actor(
            record.get(ACTOR.ACTOR_ID),
            record.get(ACTOR.FIRST_NAME),
            record.get(ACTOR.LAST_NAME),
            record.get(ACTOR.LAST_UPDATE)
        );
    }
}
