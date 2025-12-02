package configuration.di

import application.usecases.albumUseCase.DeleteAlbumUseCase
import application.usecases.albumUseCase.GetAlbumById
import application.usecases.albumUseCase.GetAlbumsByArtist
import application.usecases.albumUseCase.GetAllAlbums
import application.usecases.albumUseCase.UpdateAlbum
import application.usecases.trackUseCase.CreateTrackUseCase
import application.usecases.trackUseCase.DeleteTrackUseCase
import application.usecases.trackUseCase.GetAllTracks
import application.usecases.trackUseCase.GetTrackBy
import application.usecases.trackUseCase.*
import application.usescases.albumUseCase.*
import application.usescases.artistUseCase.*
import domain.ports.AlbumRepository
import domain.ports.ArtistRepository
import domain.ports.TracksRepository
import infrastructure.adapters.out.ExposedAlbumRepo
import infrastructure.adapters.out.ExposedArtistRepi
import infrastructure.adapters.out.ExposedTrackRepo

class AppModule {

    val artistRepository: ArtistRepository = ExposedArtistRepi()
    val albumRepository: AlbumRepository = ExposedAlbumRepo()
    val trackRepository: TracksRepository = ExposedTrackRepo()

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

    //TRACK USE CASES

    val createTrackUseCase = CreateTrackUseCase(
        trackRepository = trackRepository,
        albumRepository = albumRepository,
        artistRepository = artistRepository
    )
    val getAllTracksUseCase = GetAllTracks(
        trackRepository = trackRepository,
        albumRepository = albumRepository,
        artistRepository = artistRepository
    )
    val getTrackByIdUseCase = GetTrackBy(
        trackRepository = trackRepository,
        albumRepository = albumRepository,
        artistRepository = artistRepository
    )
    val getTracksByAlbumUseCase = GetTrackBy(
        trackRepository = trackRepository,
        albumRepository = albumRepository,
        artistRepository = artistRepository
    )
    val updateTrackUseCase = UpdateTrack(
        trackRepository = trackRepository,
        albumRepository = albumRepository,
        artistRepository = artistRepository
    )
    val deleteTrackUseCase = DeleteTrackUseCase(trackRepository)
}