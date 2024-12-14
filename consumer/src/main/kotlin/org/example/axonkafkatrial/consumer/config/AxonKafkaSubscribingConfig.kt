package org.example.axonkafkatrial.consumer.config

import org.axonframework.config.Configurer
import org.axonframework.extensions.kafka.configuration.KafkaMessageSourceConfigurer
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnExpression("'\${axon.kafka.consumer.event-processor-mode}' == 'subscribing'")
class AxonKafkaSubscribingConfig {
    @Autowired
    fun configure(
        configurer: Configurer,
        subscribableKafkaMessageSource: SubscribableKafkaMessageSource<String, ByteArray>,
    ) {
        val kafkaMessageSourceConfigurer = KafkaMessageSourceConfigurer()
        kafkaMessageSourceConfigurer.configureSubscribableSource {
            subscribableKafkaMessageSource
        }
        configurer
            .registerModule(kafkaMessageSourceConfigurer)
            .eventProcessing()
            .registerSubscribingEventProcessor("org.example.axonkafkatrial.consumer.listener") {
                subscribableKafkaMessageSource
            }
    }
}