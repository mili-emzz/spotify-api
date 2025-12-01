package infrastructure.services

import com.emiliagomez.domain.models.Track
import domain.ports.TracksRepository

class TrackService: TracksRepository {
    override suspend fun createTrack(track: Track): Track {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrackById(id: String): Track {
        TODO("Not yet implemented")
    }

    override suspend fun getTracksByAlbumId(albumId: String): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrack(
        id: String,
        track: Track
    ): Track {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrack(id: String): Boolean {
        TODO("Not yet implemented")
    }
}