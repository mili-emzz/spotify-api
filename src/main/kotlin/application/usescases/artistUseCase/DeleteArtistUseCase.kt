package application.usescases.artistUseCase

import domain.exceptions.ArtistNotFoundException
import domain.ports.ArtistRepository
import java.util.UUID

class DeleteArtistUseCase(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String): Boolean {
        return artistRepository.deleteArtist(id)
    }
}