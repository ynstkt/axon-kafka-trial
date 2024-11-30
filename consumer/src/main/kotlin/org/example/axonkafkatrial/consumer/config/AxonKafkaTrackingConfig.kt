package org.example.axonkafkatrial.consumer.config

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnExpression("'\${axon.kafka.consumer.event-processor-mode}' == 'tracking'") // 追加
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