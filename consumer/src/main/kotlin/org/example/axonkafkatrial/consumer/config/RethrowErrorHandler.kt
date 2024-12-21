package org.example.axonkafkatrial.consumer.config

import org.axonframework.eventhandling.EventMessage
import org.axonframework.eventhandling.EventMessageHandler
import org.axonframework.eventhandling.ListenerInvocationErrorHandler
import org.slf4j.LoggerFactory
import java.lang.Exception

class RethrowErrorHandler: ListenerInvocationErrorHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onError(exception: Exception, event: EventMessage<*>, eventHandler: EventMessageHandler) {
        logger.error("Rethrow event handling error: $event", exception)
        throw exception
    }
}