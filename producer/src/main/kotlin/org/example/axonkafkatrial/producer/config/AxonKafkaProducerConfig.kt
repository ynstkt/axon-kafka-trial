package org.example.axonkafkatrial.producer.config

import org.axonframework.extensions.kafka.eventhandling.producer.TopicResolver
import org.example.axonkafkatrial.producer.service.DocCreated
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class AxonKafkaProducerConfig {
    @Bean
    fun topicResolver(): TopicResolver =
        TopicResolver { eventMessage ->
            when (eventMessage.payloadType) {
                DocCreated::class.java -> Optional.of("doc-topic")
                else -> Optional.of("default-topic")
            }
        }
}