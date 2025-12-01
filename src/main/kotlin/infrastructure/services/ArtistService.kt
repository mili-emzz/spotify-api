package infrastructure.services

import DatabaseFactory.dbQuery
import com.emiliagomez.domain.models.Artist
import domain.ports.ArtistRepository
import infrastructure.database.tables.ArtistTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.Instant
import java.util.UUID

class ArtistService : ArtistRepository {

    private fun ResultRow.toArtist() = Artist(
        id = this[ArtistTable.id],
        name = this[ArtistTable.name],
        genre = this[ArtistTable.genre],
        createdAt = this[ArtistTable.createdAt],
        updatedAt = this[ArtistTable.updatedAt]
    )

    override suspend fun createArtist(artist: Artist): Artist = dbQuery {
        val now = Instant.now()

        ArtistTable.insert {
            it[id] = artist.id
            it[name] = artist.name
            it[genre] = artist.genre
            it[createdAt] = now
            it[updatedAt] = now
        }

        artist.copy(
            createdAt = now,
            updatedAt = now
        )
    }

    override suspend fun getAllArtists(): List<Artist> = dbQuery {
        ArtistTable
            .selectAll()
            .orderBy(ArtistTable.name to SortOrder.ASC)
            .map { it.toArtist() }
    }

    override suspend fun getArtistById(id: UUID): Artist? = dbQuery {
        ArtistTable
            .selectAll()
            .where { ArtistTable.id eq id }
            .map { it.toArtist() }
            .singleOrNull()  // Retorna null si no existe, sin excepciones
    }

    override suspend fun updateArtist(
        id: UUID
    ): Artist = dbQuery {
        // Verificar que el artista existe
        val existing = ArtistTable
            .selectAll()
            .where { ArtistTable.id eq id }
            .map { it.toArtist() }
            .singleOrNull()
            ?: throw NoSuchElementException("Artista con id $id no encontrado")

        val now = Instant.now()

        ArtistTable.update({ ArtistTable.id eq id }) {
            it[updatedAt] = now
        }
        existing.copy(
            updatedAt = now
        )
    }

    override suspend fun deleteArtist(id: UUID): Boolean = dbQuery {
        val deletedCount = ArtistTable.deleteWhere { ArtistTable.id eq id }
        deletedCount > 0
    }
}