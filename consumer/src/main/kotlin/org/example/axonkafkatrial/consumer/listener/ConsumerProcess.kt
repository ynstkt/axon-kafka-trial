package org.example.axonkafkatrial.consumer.listener

import org.axonframework.eventhandling.EventHandler
import org.example.axonkafkatrial.shared.event.DocCreated
import org.example.axonkafkatrial.shared.event.OtherCreated
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ConsumerProcess {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @EventHandler
    fun on(event: DocCreated) {
        logger.info("event received: $event")
    }

    @EventHandler
    fun on(event: OtherCreated) {
        logger.info("event received: $event")
    }
}