package application.usecases.albumUseCase

import application.dto.AlbumResponse
import domain.exceptions.AlbumNotFoundException
import domain.exceptions.ArtistNotFoundException
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository
import java.util.UUID

class GetAlbumById(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(id: String): AlbumResponse {

        val album = albumRepository.getAlbumById(id)
            ?: throw AlbumNotFoundException("Álbum no encontrado con id: $id")

        val artist = artistRepository.getArtistById(album.artistId.toString())
            ?: throw ArtistNotFoundException("Artista asociado al álbum no encontrado.")

        return AlbumResponse(
            id = album.id.toString(),
            title = album.title,
            releaseYear = album.releaseYear,
            artistId = album.artistId.toString(),
            artistName = artist.name,
            createdAt = album.createdAt.toString(),
            updatedAt = album.updatedAt.toString()
        )
    }
}

class GetAlbumsByArtist(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(artistId: String): List<AlbumResponse> {
        val artist = artistRepository.getArtistById(id = artistId)

        val albums = albumRepository.getAlbumsByArtistId(artistId)

        return albums.map { album ->
            AlbumResponse(
                id = album.id.toString(),
                title = album.title,
                releaseYear = album.releaseYear,
                artistId = album.artistId.toString(),
                artistName = artist.name,
                createdAt = album.createdAt.toString(),
                updatedAt = album.updatedAt.toString()
            )
        }
    }
}