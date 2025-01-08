package org.example.axonkafkatrial.consumer.config

import org.axonframework.common.jpa.EntityManagerProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.config.Configurer
import org.axonframework.config.ConfigurerModule
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.EventMessage
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration
import org.axonframework.eventhandling.deadletter.jpa.JpaSequencedDeadLetterQueue
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

    private val processingGroup = "org.example.axonkafkatrial.consumer.listener"

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
    fun threadCountConfigurerModule(): ConfigurerModule {
        val tepConfig =
            TrackingEventProcessorConfiguration
                .forParallelProcessing(2)
                .andInitialSegmentsCount(2)

        return ConfigurerModule { configurer: Configurer ->
            configurer.eventProcessing { processingConfigurer: EventProcessingConfigurer ->
                processingConfigurer.registerTrackingEventProcessorConfiguration(processingGroup) {
                    tepConfig
                }
            }
        }
    }

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

    @Bean
    fun processingGroupErrorHandlingConfigurerModule(): ConfigurerModule =
        ConfigurerModule { configurer: Configurer ->
            configurer.eventProcessing { processingConfigurer: EventProcessingConfigurer ->
                processingConfigurer.registerDefaultListenerInvocationErrorHandler {
                    RethrowErrorHandler()
                }
            }
        }

    @Bean
    fun deadLetterQueueConfigurerModule(): ConfigurerModule =
        ConfigurerModule { configurer: Configurer ->
            configurer.eventProcessing().registerDeadLetterQueue(
                processingGroup
            ) { config: org.axonframework.config.Configuration ->
                    JpaSequencedDeadLetterQueue.builder<EventMessage<*>>()
                        .processingGroup(processingGroup)
                        .entityManagerProvider(config.getComponent(EntityManagerProvider::class.java))
                        .transactionManager(config.getComponent(TransactionManager::class.java))
                        .serializer(config.serializer())
                        .build()
            }
        }

    @Bean
    fun enqueuePolicyConfigurerModule(): ConfigurerModule =
        ConfigurerModule { configurer ->
            configurer.eventProcessing()
                .registerDeadLetterPolicy(processingGroup) {
                    CustomEnqueuePolicy()
                }
        }
}