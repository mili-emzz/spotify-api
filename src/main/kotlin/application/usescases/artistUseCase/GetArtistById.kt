package application.usescases.artistUseCase

import application.dto.ArtistResponse
import domain.exceptions.ArtistNotFoundException
import domain.ports.ArtistRepository
import java.util.UUID

class GetArtistById(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String): ArtistResponse {
        val artist = artistRepository.getArtistById(id)
        return ArtistResponse(
            id = artist.id.toString(),
            name = artist.name,
            genre = artist.genre,
            createdAt = artist.createdAt.toString(),
            updatedAt = artist.updatedAt.toString()
        )
    }
}