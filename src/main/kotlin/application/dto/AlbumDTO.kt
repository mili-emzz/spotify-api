package application.dto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive


@Serializable
data class AlbumRequest(
    val title: String,
    val releaseYear: Int,
    val artistId: String
) {
    init {
        require(title.isNotBlank()) { "El título no puede estar vacío" }
        require(releaseYear in 1900..2100) { "Año inválido: $releaseYear" }
    }
}

@Serializable
data class UpdateAlbumRequest(
    val title: String?,
    val releaseYear: Int?
) {
    init {
        title?.let { require(it.isNotBlank()) { "El título no puede estar vacío" } }
        releaseYear?.let { require(it in 1900..2100) { "Año inválido: $it" } }
    }
}

@Serializable
data class AlbumResponse(
    val id: String,
    val title: String,
    val releaseYear: Int,
    val artistId: String,
    val artistName: String?,  // puede ser null si no se trae la info del artista
    val createdAt: String,
    val updatedAt: String
)
