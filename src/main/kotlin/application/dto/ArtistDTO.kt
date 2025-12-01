package application.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArtistRequest(
    val name: String,
    val genre: String
) {
    init {
        require(name.isNotBlank()) { "El nombre no puede estar vacío" }
        require(genre.isNotBlank()) { "El género no puede estar vacío" }
    }
}

@Serializable
data class UpdateArtistRequest(
    val name: String? = null,
    val genre: String? = null
) {
    init {
        name?.let { require(it.isNotBlank()) { "El nombre no puede estar vacío" } }
        genre?.let { require(it.isNotBlank()) { "El género no puede estar vacío" } }
    }
}

@Serializable
data class ArtistResponse(
    val id: String,
    val name: String,
    val genre: String,
    val createdAt: String,
    val updatedAt: String
)