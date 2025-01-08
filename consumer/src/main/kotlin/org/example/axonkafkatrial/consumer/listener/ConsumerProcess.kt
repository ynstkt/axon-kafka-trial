package org.example.axonkafkatrial.consumer.listener

import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.EventMessage
import org.axonframework.messaging.deadletter.DeadLetter
import org.example.axonkafkatrial.shared.event.DocCreated
import org.example.axonkafkatrial.shared.event.OtherCreated
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ConsumerProcess {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @EventHandler
    fun on(event: DocCreated, deadLetter: DeadLetter<EventMessage<DocCreated>>?) {
        if(deadLetter != null) {
            logger.info("handling dead letter: $deadLetter")
        }
//        sometimesThrowException()
        logger.info("event received: $event")
    }

    @EventHandler
    fun on(event: OtherCreated) {
//        sometimesThrowException()
        logger.info("event received: $event")
    }

    private var count = 0
    private var eventToFail: DocCreated? = null
    private fun sometimesThrowException() {
        count++
        logger.info("count: $count")
//        if ( count == 1 || eventToFail == event) {
//            eventToFail = event
            throw RuntimeException("test exception")
//        }
//        when(count % 3) {
//            2 -> throw RuntimeException("test exception")
//            1 -> throw IllegalStateException("test exception")
//            else -> return
//        }
    }
}