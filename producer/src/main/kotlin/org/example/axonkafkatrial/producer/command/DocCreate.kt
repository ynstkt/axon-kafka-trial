package org.example.axonkafkatrial.producer.command

import java.util.*

data class DocCreate(
    val docId: UUID,
    val body: String,
) {
    companion object {
        fun create(body: String) = DocCreate(UUID.randomUUID(), body)
    }
}