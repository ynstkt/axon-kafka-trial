package org.example.axonkafkatrial.shared.event

import java.util.*

data class DocCreated(
    val docId: UUID,
    val body: String,
)