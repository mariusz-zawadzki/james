/*
 * Copyright 2017 TomTom International B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomtom.james.controller.consul

import spock.lang.Specification


class ConsulControllerSpec extends Specification{

    def "Should parse original v1 json" () {
        given:
        def methodRefrence = "foo.bar.className#methodName"
        def json = '''{
    "sampleRate": 3, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ]
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        ip.isPresent()
        ip.get().className == "foo.bar.className"
        ip.get().methodName == "methodName"
        ip.get().sampleRate == 3
    }

    def "Should parse v1 json with owner and index" () {
        given:
        def methodRefrence = "foo.bar.className2#methodName2"
        def json = '''{
    "sampleRate": 6, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ],
    "metadata": {
        "owner": "jenkins",
        "esIndex": "test"
    } 
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        ip.isPresent()
        ip.get().className == "foo.bar.className2"
        ip.get().methodName == "methodName2"
        ip.get().sampleRate == 6
        ip.get().getMetadata().get("owner") == "jenkins"
        ip.get().getMetadata().get("esIndex") == "test"
    }

    def "Should parse v1 json with old sample rate property" () {
        given:
        def methodRefrence = "foo.bar.className2#methodName2"
        def json = '''{
    "sampleRate": 6, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ],
    "metadata": {
        "owner": "jenkins",
        "esIndex": "test"
    } 
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        ip.isPresent()
        ip.get().className == "foo.bar.className2"
        ip.get().methodName == "methodName2"
        ip.get().successSampleRate == 6
        ip.get().errorSampleRate == 6
        ip.get().getMetadata().get("owner") == "jenkins"
        ip.get().getMetadata().get("esIndex") == "test"
    }


    def "Should parse v1 json with success and error sampleRates" () {
        given:
        def methodRefrence = "foo.bar.className2#methodName2"
        def json = '''{
    "successSampleRate": 50, 
    "errorSampleRate": 90, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ],
    "metadata": {
        "owner": "jenkins",
        "esIndex": "test"
    } 
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        ip.isPresent()
        ip.get().className == "foo.bar.className2"
        ip.get().methodName == "methodName2"
        ip.get().successSampleRate == 50
        ip.get().errorSampleRate == 90
    }


    def "Should fail to parse v1 json when sampleRate and errorSampleRate are defined" () {
        given:
        def methodRefrence = "foo.bar.className2#methodName2"
        def json = '''{
    "sampleRate": 100, 
    "errorSampleRate": 90, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ],
    "metadata": {
        "owner": "jenkins",
        "esIndex": "test"
    } 
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        thrown(IllegalStateException)
    }

    def "Should fail to parse v1 json when sampleRate and successSampleRate are defined" () {
        given:
        def methodRefrence = "foo.bar.className2#methodName2"
        def json = '''{
    "sampleRate": 100, 
    "successSampleRate": 90, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ],
    "metadata": {
        "owner": "jenkins",
        "esIndex": "test"
    } 
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        thrown(IllegalStateException)
    }

    def "Should fail to parse v1 json when sampleRate and successSample are defined" () {
        given:
        def methodRefrence = "foo.bar.className2#methodName2"
        def json = '''{
    "sampleRate": 100, 
    "successSampleRate": 90, 
    "version": 1, 
    "script": [
        "// First line of Information Point script",
        "// Second line of Information Point script"
    ],
    "metadata": {
        "owner": "jenkins",
        "esIndex": "test"
    } 
    }'''

        when:
        def ip = InformationPointDTOParser.parse(json, methodRefrence)

        then:
        thrown(IllegalStateException)
    }
}
