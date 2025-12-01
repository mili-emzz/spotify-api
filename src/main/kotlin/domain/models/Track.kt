package com.emiliagomez.domain.models

import java.time.Instant
import java.util.UUID

data class Track(
    val id: UUID,
    val albumId: UUID,
    val title: String,
    val duration: Int,
    val createdAt: Instant,
    val updatedAt: Instant
){
    init{
        require(duration > 0) { "La duración no puede ser 0" }
        require(title.isNotBlank()) { "El titulo no puede estar vacío" }
    }
}