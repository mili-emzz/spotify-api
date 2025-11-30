package domain.interfaces

import com.emiliagomez.domain.models.Album

interface AlbumInterface {

    suspend fun createAlbum(album: Album): Album
    suspend fun getAllAlbums(): List<Album>
    suspend fun getAlbumById(id: String): Album
    suspend fun updateAlbum(id: String, album: Album): Album
    suspend fun deleteAlbum(id: String): Boolean
}