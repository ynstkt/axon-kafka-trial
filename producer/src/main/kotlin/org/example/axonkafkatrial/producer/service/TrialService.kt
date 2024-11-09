package org.example.axonkafkatrial.producer.service

import org.axonframework.commandhandling.CommandHandler
import org.example.axonkafkatrial.producer.command.DocCreate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TrialService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @CommandHandler
    fun run(command: DocCreate) {
        logger.info("command accepted: $command")
    }
}