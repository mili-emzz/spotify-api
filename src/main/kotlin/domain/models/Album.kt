package com.emiliagomez.domain.models

import java.time.OffsetDateTime
import java.util.UUID
import java.time.Instant

data class Album(
    val id: UUID,
    val artistId: UUID,
    val title: String,
    val releaseYear: Int,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    init {
    require(title.isNotBlank()) { "El título no puede estar vacío" }
    require(releaseYear in 1900..2100) { "Año de lanzamiento inválido: $releaseYear" }
    }
}