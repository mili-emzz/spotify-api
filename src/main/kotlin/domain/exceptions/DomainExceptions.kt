package domain.exceptions

sealed class DomainException(message: String) : Exception(message)

class ArtistNotFoundException(message: String) : Exception(message)
