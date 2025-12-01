package application.usecases.albumUseCase

import application.dto.AlbumResponse
import com.emiliagomez.domain.models.Album
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository

class GetAllAlbums(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(): List<AlbumResponse> {
        val albums = albumRepository.getAllAlbums()
        return albums.map { it.toResponse() }
    }

    private suspend fun Album.toResponse(): AlbumResponse {
        val artist = try {
            artistRepository.getArtistById(this.artistId.toString())
        } catch (e: Exception) {
            null
        }

        return AlbumResponse(
            id = this.id.toString(),
            title = this.title,
            releaseYear = this.releaseYear,
            artistId = this.artistId.toString(),
            artistName = artist?.name,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString()
        )
    }
}
