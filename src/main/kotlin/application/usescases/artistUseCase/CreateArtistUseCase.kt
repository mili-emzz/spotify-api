package application.usescases.artistUseCase

import application.dto.ArtistRequest
import application.dto.ArtistResponse
import com.emiliagomez.domain.models.Artist
import domain.ports.ArtistRepository
import java.time.Instant
import java.util.UUID

class CreateArtistUseCase(
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(request: ArtistRequest): ArtistResponse {
        val artist = Artist(
            id = UUID.randomUUID(),
            name = request.name,
            genre = request.genre,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        // Guardar en la BD
        val savedArtist = artistRepository.createArtist(artist)

        return ArtistResponse(
            id = savedArtist.id.toString(),
            name = savedArtist.name,
            genre = savedArtist.genre,
            createdAt = savedArtist.createdAt.toString(),
            updatedAt = savedArtist.updatedAt.toString()
        )
    }
}