package application.usescases.artistUseCase

import application.dto.ArtistResponse
import domain.exceptions.ArtistNotFoundException
import domain.ports.ArtistRepository
import java.util.UUID

class GetArtistById(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String): ArtistResponse {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw ArtistNotFoundException("ID inválido: $id") as Throwable
        } catch (e: Exception) {
            throw e
        }

        val artist = artistRepository.getArtistById(uuid)
            ?: throw ArtistNotFoundException("Artista no encontrado con id: $id") as Throwable

        return ArtistResponse(
            id = artist.id.toString(),
            name = artist.name,
            genre = artist.genre,
            createdAt = artist.createdAt.toString(),
            updatedAt = artist.updatedAt.toString()
        )
    }
}