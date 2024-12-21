package org.example.axonkafkatrial.consumer.config

import org.axonframework.eventhandling.EventMessage
import org.axonframework.messaging.deadletter.DeadLetter
import org.axonframework.messaging.deadletter.Decisions
import org.axonframework.messaging.deadletter.EnqueueDecision
import org.axonframework.messaging.deadletter.EnqueuePolicy
import org.example.axonkafkatrial.shared.event.OtherCreated
import org.slf4j.LoggerFactory

class CustomEnqueuePolicy: EnqueuePolicy<EventMessage<*>> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun decide(letter: DeadLetter<out EventMessage<*>>, cause: Throwable?): EnqueueDecision<EventMessage<*>> {
        logger.info("deciding on dead letter: $letter")

        if (letter.message().payload is OtherCreated) {
            return Decisions.doNotEnqueue()
        }

        val retries = letter.diagnostics().getOrDefault("retries", -1) as Int
        logger.info("retries: $retries")

        if (retries < 2) {
            return Decisions.requeue(
                cause
            ) { l: DeadLetter<out EventMessage<*>> ->
                l.diagnostics().and("retries", retries + 1)
            }
        }

        return Decisions.evict()
    }
}