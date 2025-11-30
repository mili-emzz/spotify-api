package infrastructure.services

import com.emiliagomez.domain.models.Artist
import domain.ports.ArtistRepository
import infrastructure.database.tables.ArtistTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow

class ArtistService: ArtistRepository {

    private fun ResultRow.toArtist()= Artist(
        id = this[ArtistTable.id],
        name = this[ArtistTable.name],
        genre = this[ArtistTable.genre],
        createdAt = this[ArtistTable.createdAt],
        updatedAt = this[ArtistTable.updatedAt]
    )

    override suspend fun createArtist(artist: Artist): Artist {
        TODO("Not yet implemented")
    }

    override suspend fun getAllArtists(): List<Artist> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtistById(id: String): Artist = dbQuery {
        TODO("Not yet implemented")
    }

    override suspend fun updateArtist(
        id: String,
        artist: Artist
    ): Artist {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArtist(id: String): Boolean {
        TODO("Not yet implemented")
    }


}