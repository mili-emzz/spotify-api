package domain.ports

import com.emiliagomez.domain.models.Artist
import java.util.UUID

interface ArtistRepository {
    suspend fun createArtist(artist: Artist): Artist
    suspend fun getAllArtists(): List<Artist>
    suspend fun getArtistById(id: UUID): Artist?
    suspend fun updateArtist(id: UUID): Artist
    suspend fun deleteArtist(id: UUID): Boolean
}