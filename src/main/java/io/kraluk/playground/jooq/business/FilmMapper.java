package io.kraluk.playground.jooq.business;

import io.kraluk.playground.jooq.db.playground.tables.pojos.Film;
import io.kraluk.playground.jooq.db.playground.tables.records.FilmRecord;
import org.jooq.RecordMapper;

/**
 * Simple {@link FilmRecord} to {@link Film} mapper
 */
class FilmMapper implements RecordMapper<FilmRecord, Film> {

	@Override
	public Film map(final FilmRecord record) {
		return new Film(
				record.getFilmId(),
				record.getTitle(),
				record.getDescription(),
				record.getReleaseYear(),
				record.getLanguageId(),
				record.getOriginalLanguageId(),
				record.getRentalDuration(),
				record.getRentalRate(),
				record.getLength(),
				record.getReplacementCost(),
				record.getRating(),
				record.getSpecialFeatures(),
				record.getLastUpdate()
		);
	}
}
