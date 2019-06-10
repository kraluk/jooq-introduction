package io.kraluk.playground.jooq.business

import io.kraluk.playground.jooq.test.IntegrationSpecification
import spock.lang.Ignore

class ActorRepositorySpec extends IntegrationSpecification {

    ActorRepository repository = inject(ActorRepository)

    def "should count movies by actor Ids"() {
        when:
        def result = repository.countMoviesByActorId()

        then:
        result.size() >= 200
    }

    def "should find latest actor"() {
        when:
        def result = repository.findLatestActor()

        then:
        result == "THORA TEMPLE"
    }

    @Ignore("Not working properly under H2")
    def "should find latest actor via 'procedure'"() {
        when:
        def result = repository.findLatestActorViaProcedureDirectly()

        then:
        result == "THORA TEMPLE"
    }

    def "should find latest actor via Routines class"() {
        when:
        def result = repository.findLatestActorViaRoutines()

        then:
        result == "THORA TEMPLE"
    }

    def "should find all"() {
        when:
        def result = repository.findAll()

        then:
        result != null
        and:
        result.size() == 100
    }
}
