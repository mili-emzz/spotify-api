package domain.ports

import com.emiliagomez.domain.models.Album
import java.util.UUID

interface AlbumRepository {
    suspend fun createAlbum(album: Album): Album
    suspend fun getAllAlbums(): List<Album>
    suspend fun getAlbumById(id: String): Album?
    suspend fun getAlbumsByArtistId(artistId: String): List<Album>
    suspend fun updateAlbum(id: String, album: Album): Album
    suspend fun deleteAlbum(id: String): Boolean
}