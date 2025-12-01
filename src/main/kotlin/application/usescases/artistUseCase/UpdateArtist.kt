package application.usescases.artistUseCase

import application.dto.ArtistResponse
import application.dto.UpdateArtistRequest
import domain.exceptions.ArtistNotFoundException
import domain.ports.ArtistRepository
import java.util.UUID

class UpdateArtist(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String, request: UpdateArtistRequest): ArtistResponse {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw ArtistNotFoundException("ID inválido: $id") as Throwable
        } catch (e: Exception) {
            throw e
        }

        val updatedArtist = try {
            artistRepository.updateArtist(uuid)
        } catch (e: NoSuchElementException) {
            throw ArtistNotFoundException("Artista no encontrado con id: $id")
        }

        return ArtistResponse(
            id = updatedArtist.id.toString(),
            name = updatedArtist.name,
            genre = updatedArtist.genre,
            createdAt = updatedArtist.createdAt.toString(),
            updatedAt = updatedArtist.updatedAt.toString()
        )
    }
}