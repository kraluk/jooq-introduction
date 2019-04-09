package io.kraluk.playground.jooq.business

import io.kraluk.playground.jooq.test.IntegrationSpecification
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.jooq.exception.DetachedException

import static io.kraluk.playground.jooq.business.FilmFixtures.changeRentalRate
import static io.kraluk.playground.jooq.business.FilmFixtures.createFilmRecord

class FilmRecordRepositorySpec extends IntegrationSpecification {

    DSLContext context = inject(DSLContext)

    FilmRecordRepository repository = inject(FilmRecordRepository)

    def "should not save a film represented by not attached Record"() {
        given:
        def record = createFilmRecord()

        when:
        repository.save(record)

        then:
        def e = thrown(DetachedException)
        and:
        e.message.contains("Cannot execute query. No Connection configured")
    }

    def "should save a film represented by attached Record"() {
        given:
        def record = createFilmRecord()
        and:
        record.attach(context.configuration())

        when:
        def saved = repository.save(record)

        then:
        with(saved) {
            filmId != null
            lastUpdate != null
        }
    }

    def "should find a film"() {
        given:
        def id = 2

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
        def id = 3
        and:
        def film = repository.find(id)
        and:
        film.isPresent()
        and:
        def filmWithRate = changeRentalRate(film.get())

        when:
        def updated = repository.update(filmWithRate) // watch out on roundings! they are performed after refresh()!

        then:
        with(updated) {
            id == film.get().getFilmId()
            rentalRate == 1.5
        }
    }

    def "should not delete a film due to foreign key on Actor table"() {
        given:
        def id = 2
        and:
        def record = repository.find(id)
        and:
        record.isPresent()
        and:
        def film = record.get()

        when:
        repository.delete(film)

        then:
        def e = thrown(DataAccessException)
        and:
        e.message.contains("Referential integrity constraint violation: \"FK_FILM_ACTOR_FILM")
    }
}
