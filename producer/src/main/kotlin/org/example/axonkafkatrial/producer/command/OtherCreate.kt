package org.example.axonkafkatrial.producer.command

import java.util.*

data class OtherCreate(
    val id: UUID,
    val body: String,
)