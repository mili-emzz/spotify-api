package application.usescases.artistUseCase

import domain.exceptions.ArtistNotFoundException
import domain.ports.ArtistRepository
import java.util.UUID

class DeleteArtistUseCase(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String): Boolean {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            throw ArtistNotFoundException("ID inválido: $id") as Throwable
        } catch (e: Exception) {
            throw e
        }

        val deleted = artistRepository.deleteArtist(uuid)

        if (!deleted) {
            throw ArtistNotFoundException("Artista no encontrado con id: $id")
        }

        return true
    }
}