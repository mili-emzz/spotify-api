package application.usecases.trackUseCase

import application.dto.TrackResponse
import com.emiliagomez.domain.models.Track
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository
import domain.ports.TracksRepository

class GetAllTracks(
    private val trackRepository: TracksRepository,
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    suspend fun execute(): List<TrackResponse> {
        val tracks = trackRepository.getAllTracks()
        return tracks.map { it.toResponse(albumRepository, artistRepository) }
    }

    private suspend fun Track.toResponse(
        albumRepository: AlbumRepository,
        artistRepository: ArtistRepository
    ): TrackResponse {
        val album = try {
            albumRepository.getAlbumById(this.albumId.toString())
        } catch (e: Exception) {
            null
        }

        val artist = if (album != null) {
            try {
                artistRepository.getArtistById(album.artistId.toString())
            } catch (e: Exception) {
                null
            }
        } else null

        return TrackResponse(
            id = this.id.toString(),
            title = this.title,
            duration = this.duration,
            durationFormatted = formatDuration(this.duration),
            albumId = this.albumId.toString(),
            albumTitle = album?.title,
            artistName = artist?.name,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString()
        )
    }

    private fun formatDuration(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%02d:%02d", minutes, secs)
        }
    }
}