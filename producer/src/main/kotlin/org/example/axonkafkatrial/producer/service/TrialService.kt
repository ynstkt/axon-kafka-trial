package org.example.axonkafkatrial.producer.service

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.gateway.EventGateway
import org.example.axonkafkatrial.producer.command.DocCreate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TrialService(private val eventGateway: EventGateway) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @CommandHandler
    fun run(command: DocCreate) {
        logger.info("command accepted: $command")
        val event = DocCreated(command.docId, command.body)
        eventGateway.publish(event)
    }
}

data class DocCreated(
    val docId: UUID,
    val body: String,
)