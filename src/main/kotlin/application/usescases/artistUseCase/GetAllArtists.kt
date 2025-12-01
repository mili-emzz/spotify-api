package application.usescases.artistUseCase

import application.dto.ArtistResponse
import domain.ports.ArtistRepository

class GetAllArtists(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(): List<ArtistResponse> {
        val artists = artistRepository.getAllArtists()

        return artists.map { artist ->
            ArtistResponse(
                id = artist.id.toString(),
                name = artist.name,
                genre = artist.genre,
                createdAt = artist.createdAt.toString(),
                updatedAt = artist.updatedAt.toString()
            )
        }
    }
}