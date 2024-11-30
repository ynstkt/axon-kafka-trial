package org.example.axonkafkatrial.consumer.config

import org.axonframework.eventhandling.EventMessage
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory
import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.KafkaEventMessage
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource
import org.axonframework.serialization.Serializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AxonKafkaConsumerConfig {
    @Value("\${application.kafka.topics}")
    private val topics: List<String>? = null

    @Bean
    fun streamableKafkaMessageSource(
        consumerFactory: ConsumerFactory<String, ByteArray>,
        fetcher: Fetcher<String, ByteArray, KafkaEventMessage>,
        serializer: Serializer,
    ): StreamableKafkaMessageSource<String, ByteArray> =
        StreamableKafkaMessageSource.builder<String, ByteArray>()
            .topics(topics)
            .consumerFactory(consumerFactory)
            .fetcher(fetcher)
            .serializer(serializer)
            .build()

    @Bean
    fun subscribableKafkaMessageSource(
        consumerFactory: ConsumerFactory<String, ByteArray>,
        fetcher: Fetcher<String, ByteArray, EventMessage<*>>,
        serializer: Serializer,
    ): SubscribableKafkaMessageSource<String, ByteArray> =
        SubscribableKafkaMessageSource.builder<String, ByteArray>()
            .topics(topics)
            .groupId("axon-kafka-trial-consumer-group")
            .consumerFactory(consumerFactory)
            .fetcher(fetcher)
            .serializer(serializer)
            .build()
}