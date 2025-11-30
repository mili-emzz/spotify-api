package domain.exceptions

sealed class DomainException(message: String) : Exception(message)

class ArtistNotFoundException(uuid: String) :
    DomainException("Artista con UUID $uuid no encontrado")

class AlbumNotFoundException(uuid: String) :
    DomainException("Álbum con UUID $uuid no encontrado")

class InvalidArtistDataException(message: String) :
    DomainException(message)