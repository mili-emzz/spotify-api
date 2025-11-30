package com.emiliagomez.domain.models

import java.time.OffsetDateTime
import java.util.UUID

class Album(
    val id: UUID,
    val artistId: UUID,
    val title: String,
    val releaseYear: Int,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
){
}