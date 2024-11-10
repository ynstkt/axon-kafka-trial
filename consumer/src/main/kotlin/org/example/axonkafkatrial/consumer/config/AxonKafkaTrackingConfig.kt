package org.example.axonkafkatrial.consumer.config

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class AxonKafkaTrackingConfig {
    @Autowired
    fun configure(
        config: EventProcessingConfigurer,
        streamableKafkaMessageSource: StreamableKafkaMessageSource<String, ByteArray>,
    ) {
        config.registerTrackingEventProcessor("org.example.axonkafkatrial.consumer.listener") {
            streamableKafkaMessageSource
        }
    }
}