package io.kraluk.playground.jooq.business

import io.kraluk.playground.jooq.test.IntegrationSpecification

class ActorRepositorySpec extends IntegrationSpecification {

    ActorRepository repository = inject(ActorRepository)

    def "should count movies by actor Ids"() {
        when:
        def result = repository.countMoviesByActorId()

        then:
        result.size() >= 200
    }
}
