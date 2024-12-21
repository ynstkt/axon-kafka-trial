package org.example.axonkafkatrial.consumer.service

import org.axonframework.config.EventProcessingConfiguration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class StreamingProcessorService(private val config: EventProcessingConfiguration) {
    private val processingGroup = "org.example.axonkafkatrial.consumer.listener"

//    @Scheduled(fixedDelayString = "60000")
    fun retryAnySequence() {
        config.sequencedDeadLetterProcessor(processingGroup)
            .ifPresent { processor -> processor.processAny() }
    }

    @Scheduled(fixedDelayString = "60000")
    fun retryAllSequences() {
        val optionalLetterProcessor = config.sequencedDeadLetterProcessor(processingGroup)
        if (!optionalLetterProcessor.isPresent) {
            return
        }
        val letterProcessor = optionalLetterProcessor.get()

        // Retrieve all the dead lettered event sequences:
        val deadLetterSequences = config.deadLetterQueue(processingGroup)
            .map { it.deadLetters() }
            .orElseThrow { IllegalArgumentException("No such Processing Group") }

        // Iterate over all sequences:
        for (sequence in deadLetterSequences) {
            val sequenceIterator = sequence.iterator()
            val firstLetterId = sequenceIterator.next()
                .message()
                .identifier

            // SequencedDeadLetterProcessor#process automatically retries an entire sequence.
            // Hence, we only need to filter on the first entry of the sequence:
            letterProcessor.process { deadLetter ->
                deadLetter.message().identifier == firstLetterId
            }
        }
    }
}