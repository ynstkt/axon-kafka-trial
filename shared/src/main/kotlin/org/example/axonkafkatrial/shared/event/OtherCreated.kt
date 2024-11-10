package org.example.axonkafkatrial.shared.event

import java.util.*

data class OtherCreated(
    val id: UUID,
    val body: String,
)