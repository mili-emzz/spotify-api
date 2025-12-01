package domain.exceptions

sealed class DomainException(message: String) : Exception(message)

class ArtistNotFoundException(message: String) : Exception(message)

class AlbumNotFoundException(message: String) : Exception(message)

class TrackNotFoundException(message: String) : Exception(message)