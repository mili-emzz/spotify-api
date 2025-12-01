package application.usescases.albumUseCase
import application.dto.AlbumRequest
import application.dto.AlbumResponse
import com.emiliagomez.domain.models.Album
import domain.exceptions.ArtistNotFoundException
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository
import java.time.Instant
import java.util.UUID

class CreateAlbumUseCase(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(request: AlbumRequest): AlbumResponse {
        val album = Album(
            id = UUID.randomUUID(),
            artistId = UUID.fromString(request.artistId),
            title = request.title,
            releaseYear = request.releaseYear,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val artist = artistRepository.getArtistById(request.artistId)
        val savedAlbum = albumRepository.createAlbum(album)

        return AlbumResponse(
            id = savedAlbum.id.toString(),
            title = savedAlbum.title,
            releaseYear = savedAlbum.releaseYear,
            artistId = savedAlbum.artistId.toString(),
            artistName = artist.name,
            createdAt = savedAlbum.createdAt.toString(),
            updatedAt = savedAlbum.updatedAt.toString()
        )
    }
}