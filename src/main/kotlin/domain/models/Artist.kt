package com.emiliagomez.domain.models

import java.time.OffsetDateTime
import java.util.UUID

class Artist(
    val id: UUID,
    val name: String,
    val genre: String?,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)