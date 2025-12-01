package com.emiliagomez.domain.models

import java.time.Instant
import java.util.UUID

data class Artist(
    val id: UUID,
    val name: String,
    val genre: String,
    val createdAt: Instant,
    val updatedAt: Instant
){
    init {
        require(name.isNotBlank()) { "El nombre del artista no puede estar vacío" }
        require(genre.isNotBlank()) { "El género no puede estar vacío" }
    }
}