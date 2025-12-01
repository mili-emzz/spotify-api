package configuration.di

import application.usescases.albumUseCase.*
import application.usescases.artistUseCase.*
import application.usescases.trackUseCase.*
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository
import domain.ports.TracksRepository
import infrastructure.services.AlbumService
import infrastructure.services.ArtistService
import infrastructure.services.TrackService

class AppModule {

    val artistRepository: ArtistRepository = ArtistService()
    val albumRepository: AlbumRepository = AlbumService()
    val trackRepository: TracksRepository = TrackService()

    // ARTIST USE CASES
    val createArtistUseCase = CreateArtistUseCase(artistRepository)
    val getAllArtistsUseCase = GetAllArtists(artistRepository)
    val getArtistByIdUseCase = GetArtistById(artistRepository)
    val updateArtistUseCase = UpdateArtist(artistRepository)
    val deleteArtistUseCase = DeleteArtistUseCase(artistRepository)


}