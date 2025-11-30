package com.emiliagomez.domain.models

import java.time.OffsetDateTime
import java.util.UUID

class Track(
    val id: UUID,
    val albumId: UUID,
    val title : String,
    val duration: Int,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)