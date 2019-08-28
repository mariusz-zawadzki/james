package com.tomtom.james.queue

import spock.lang.Specification

class SplittingQueueSpec extends Specification {

    SplittingQueue<String> splittingQueue = new SplittingQueue<>(20, 20, true);

    def "Should be able to get queue multiple times more than Integer.MAX_VALUE"() {
        given:
        splittingQueue.currentCounter = Integer.MAX_VALUE-10
        when:
        200.times {
            splittingQueue.offer("")
        }
        then:
        noExceptionThrown()
    }
}
