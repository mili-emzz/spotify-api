package application.usecases.albumUseCase

import domain.exceptions.AlbumNotFoundException
import domain.ports.AlbumRepository
import java.util.UUID

class DeleteAlbumUseCase(
    private val albumRepository: AlbumRepository
) {
    suspend fun execute(id: String): Boolean {
        return albumRepository.deleteAlbum(id)
    }
}