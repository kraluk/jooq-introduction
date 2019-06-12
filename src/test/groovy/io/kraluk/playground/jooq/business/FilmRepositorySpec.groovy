package io.kraluk.playground.jooq.business

import io.kraluk.playground.jooq.db.playground.tables.pojos.Actor
import io.kraluk.playground.jooq.db.playground.tables.pojos.Film
import io.kraluk.playground.jooq.test.IntegrationSpecification
import org.jooq.exception.DataAccessException

import static io.kraluk.playground.jooq.business.FilmFixtures.changeRentalRate
import static io.kraluk.playground.jooq.business.FilmFixtures.createFilm

class FilmRepositorySpec extends IntegrationSpecification {

    FilmRepository repository = inject(FilmRepository)

    def "should save a film"() {
        given:
        def film = createFilm()

        when:
        def saved = repository.save(film)

        then:
        with(saved) {
            filmId != null
            lastUpdate != null
        }
    }

    def "should find a film"() {
        given:
        def id = 1

        when:
        def found = repository.find(id)

        then:
        with(found) {
            isPresent()
            get().getFilmId() == id
        }
    }

    def "should find all films"() {
        when:
        def films = repository.findAll()

        then:
        films.size() > 0
    }

    def "should update a film"() {
        given:
        def id = 1
        and:
        def film = repository.find(id)
        and:
        film.isPresent()
        and:
        def filmWithRate = changeRentalRate(film.get())

        when:
        def updated = repository.update(filmWithRate)

        then:
        with(updated) {
            id == film.get().getFilmId()
            rentalRate == film.get().getRentalRate() * 2
        }
    }

    def "should not delete a film due to foreign key on Actor table"() {
        given:
        def id = 1

        when:
        repository.delete(id)

        then:
        def e = thrown(DataAccessException)
        and:
        e.message.contains("Referential integrity constraint violation: \"FK_FILM_ACTOR_FILM")
    }

    def "should find last film's id"() {
        when:
        def id = repository.findLastId()

        then:
        id >= 1_000
    }

    def "should find film Ids grouped by their release years"() {
        when:
        def result = repository.findIdsGropedByReleaseYear()

        then:
        with(result) {
            keySet().size() == 5
            entrySet().size() == 5
        }
    }

    def "should find the last updated film"() {
        when:
        def lastUpdated = repository.findLastUpdated()

        then:
        lastUpdated.size() >= 1
    }

    def "should find films with their actors"() {
        when:
        def result = repository.findWithActors()

        then:
        result.size() > 0
    }
}
