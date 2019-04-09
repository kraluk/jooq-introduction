package io.kraluk.playground.jooq

import spock.lang.Specification

class JooqPlaygroundAppSpec extends Specification {

    def "should start the app"() {
        when:
        JooqPlaygroundApp.main()

        then:
        noExceptionThrown()
    }
}
