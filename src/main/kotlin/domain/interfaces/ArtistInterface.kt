package domain.interfaces

import com.emiliagomez.domain.models.Artist

interface ArtistInterface {
    suspend fun createArtist(artist: Artist): Artist
    suspend fun getAllArtists(): List<Artist>
    suspend fun getArtistById(id: String): Artist
    suspend fun updateArtist(id: String, artist: Artist): Artist
    suspend fun deleteArtist(id: String): Boolean
}