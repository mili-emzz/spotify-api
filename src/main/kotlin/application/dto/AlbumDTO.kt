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


//serializar personalizado para que id artist acepte numero o cadena
object StringOrNumberSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("StringOrNumber", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        // JsonDecoder para inspeccionar el tipo
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("This serializer can only be used with Json") as Throwable

        val element = jsonDecoder.decodeJsonElement()

        return when (element) {
            is JsonPrimitive -> {
                when {
                    element.isString -> element.content
                    else -> element.content // números, booleanos, etc.
                }
            }
            else -> throw SerializationException("Expected a primitive value")
        }
    }
}


@Serializable
data class AlbumResponse(
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val releaseYear: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class AlbumRequest(
    val title: String,
    val releaseYear: String
)

@Serializable
data class UpdateAlbumRequest(
    val title: String?,
    val releaseYear: Int?
)
