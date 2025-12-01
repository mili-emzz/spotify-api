package application.usescases.artistUseCase

import application.dto.ArtistResponse
import application.dto.UpdateArtistRequest
import com.emiliagomez.domain.models.Artist
import domain.exceptions.ArtistNotFoundException
import domain.ports.ArtistRepository
import java.util.UUID

class UpdateArtist(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String, request: UpdateArtistRequest): ArtistResponse {

        val currentArtist = artistRepository.getArtistById(id)

        val updatedArtist = Artist(
            id = currentArtist.id,
            name = request.name ?: currentArtist.name,
            genre = request.genre ?: currentArtist.genre,
            createdAt = currentArtist.createdAt,
            updatedAt = currentArtist.updatedAt
        )

        val artist = artistRepository.updateArtist(id, updatedArtist)


        return ArtistResponse(
            id = updatedArtist.id.toString(),
            name = updatedArtist.name,
            genre = updatedArtist.genre,
            createdAt = updatedArtist.createdAt.toString(),
            updatedAt = updatedArtist.updatedAt.toString()
        )
    }
}