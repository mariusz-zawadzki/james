package com.tomtom.james.common.api.informationpoint

import spock.lang.Specification

class InformationPointSpec extends Specification {

    def "Should build information point with default sample rates."() {
        when:
        def build = defaultBuilder().build()
        then:
        build.errorSampleRate == 100.0
        build.successSampleRate == 100.0
        build.sampleRate == 100
    }

    def "Should build information point with just sample rates."() {
        when:
        def build = defaultBuilder().withSampleRate(50).build()
        then:
        build.errorSampleRate == 50.0
        build.successSampleRate == 50.0
        build.sampleRate == 50
    }

    def "Should build information point with default error sample rate when it's missing."() {
        when:
        def build = defaultBuilder().withSuccessSampleRate(0.6).build()
        then:
        build.errorSampleRate == 100.0
        build.successSampleRate == 0.6
        build.sampleRate == 100
    }

    def "Should build information point with default success sample rate when it's missing."() {
        when:
        def build = defaultBuilder().withErrorSampleRate(90.0).build()
        then:
        build.errorSampleRate == 90.0
        build.successSampleRate == 100.0
        build.sampleRate == 100
    }

    def "Should fail when sampleRate and successSampleRate are set."() {
        when:
        def build = defaultBuilder()
                .withSampleRate(90)
                .withSuccessSampleRate(25.0)
                .build()
        then:
        def exceptions = thrown(IllegalStateException)
        exceptions.message == "Cannot set successSampleRate when sampleRate is set."
    }

    def "Should fail when sampleRate and errorSampleRate are set."() {
        when:
        def build = defaultBuilder()
                .withSampleRate(90)
                .withErrorSampleRate(25.0)
                .build()
        then:
        def exceptions = thrown(IllegalStateException)
        exceptions.message == "Cannot set errorSampleRate when sampleRate is set."
    }

    protected InformationPoint.Builder defaultBuilder() {
        InformationPoint.builder().withClassName("someClassName").withMethodName("someMethod")
    }
}
