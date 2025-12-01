package application.usecases.trackUseCase

import domain.ports.TracksRepository

class DeleteTrackUseCase(
    private val trackRepository: TracksRepository
) {
    suspend fun execute(id: String): Boolean {
        return trackRepository.deleteTrack(id)
    }
}