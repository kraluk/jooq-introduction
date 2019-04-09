package io.kraluk.playground.jooq.business

import io.kraluk.playground.jooq.db.playground.enums.FilmRating
import io.kraluk.playground.jooq.db.playground.tables.pojos.Film
import io.kraluk.playground.jooq.db.playground.tables.records.FilmRecord

class FilmFixtures {

    static def createFilm() {
        return new Film(
                null,
                "Title",
                "Description",
                "1900",
                (short) 1,
                (short) 1,
                (short) 1,
                1.5,
                (short) 1,
                1.5,
                FilmRating.G,
                "Commentaries",
                null
        )
    }

    static def changeRentalRate(Film value) {
        return new Film(
                value.filmId,
                value.title,
                value.description,
                value.releaseYear,
                value.languageId,
                value.originalLanguageId,
                value.rentalDuration,
                value.rentalRate != null ? value.rentalRate * 2 : null,
                value.length,
                value.replacementCost,
                value.rating,
                value.specialFeatures,
                value.lastUpdate,
        )
    }

    static def createFilmRecord() {
        return io.kraluk.playground.jooq.db.playground.tables.Film.FILM.newRecord()
                .setFilmId(null)
                .setTitle("Title")
                .setDescription("Description")
                .setReleaseYear("1900")
                .setLanguageId((short) 1)
                .setOriginalLanguageId((short) 1)
                .setRentalDuration((short) 1)
                .setRentalRate(1.5)
                .setLength((short) 1)
                .setRating(FilmRating.G)
                .setSpecialFeatures("Commentaries")
        //.setLastUpdate(null) -- we cannot use nulls directly!
    }

    static def changeRentalRate(FilmRecord record) {
        return record.setRentalRate(record.getRentalRate() * 0.5)
    }
}
