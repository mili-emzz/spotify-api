package configuration.di

import application.usecases.albumUseCase.DeleteAlbumUseCase
import application.usecases.albumUseCase.GetAlbumById
import application.usecases.albumUseCase.GetAlbumsByArtist
import application.usecases.albumUseCase.GetAllAlbums
import application.usecases.albumUseCase.UpdateAlbum
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

    // ALBUMS USE CASES
    val createAlbumUseCase = CreateAlbumUseCase(albumRepository, artistRepository)
    val getAllAlbumsUseCase = GetAllAlbums(albumRepository, artistRepository)
    val getAlbumByIdUseCase = GetAlbumById(albumRepository, artistRepository)
    val getAlbumsByArtistUseCase = GetAlbumsByArtist(albumRepository, artistRepository)
    val updateAlbumUseCase = UpdateAlbum(albumRepository, artistRepository)
    val deleteAlbumUseCase = DeleteAlbumUseCase(albumRepository)

}