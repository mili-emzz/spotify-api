package domain.interfaces

import com.emiliagomez.domain.models.Track

interface TracksInterface {
    suspend fun createTrack(track: Track): Track
    suspend fun getAllTracks(): List<Track>
    suspend fun getTrackById(id: String): Track
    suspend fun getTracksByAlbumId(albumId: String): List<Track>
    suspend fun updateTrack(id: String, track: Track): Track
    suspend fun deleteTrack(id: String): Boolean
}