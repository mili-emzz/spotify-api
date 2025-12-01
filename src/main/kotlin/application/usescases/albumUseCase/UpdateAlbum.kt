package application.usecases.albumUseCase

import application.dto.AlbumResponse
import application.dto.UpdateAlbumRequest
import com.emiliagomez.domain.models.Album
import domain.exceptions.AlbumNotFoundException
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository
import java.util.UUID

class UpdateAlbum(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String, request: UpdateAlbumRequest): AlbumResponse {

        val currentAlbum = albumRepository.getAlbumById(id)
            ?: throw AlbumNotFoundException("Álbum con ID $id no encontrado.")

        val updatedAlbum = Album(
            id = currentAlbum.id,
            title = request.title ?: currentAlbum.title,
            releaseYear = request.releaseYear ?: currentAlbum.releaseYear,
            artistId = currentAlbum.artistId,
            createdAt = currentAlbum.createdAt,
            updatedAt = currentAlbum.updatedAt
        )

        val album = albumRepository.updateAlbum(id, updatedAlbum)
        val artist = artistRepository.getArtistById(album.artistId.toString())

        return AlbumResponse(
            id = updatedAlbum.id.toString(),
            title = updatedAlbum.title,
            releaseYear = updatedAlbum.releaseYear,
            artistId = updatedAlbum.artistId.toString(),
            artistName = artist?.name,
            createdAt = updatedAlbum.createdAt.toString(),
            updatedAt = updatedAlbum.updatedAt.toString()
        )
    }
}