package infrastructure.services

import DatabaseFactory.dbQuery
import com.emiliagomez.domain.models.Album
import com.emiliagomez.domain.models.Artist
import domain.exceptions.AlbumNotFoundException
import domain.exceptions.ArtistNotFoundException
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
        try {
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
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllArtists(): List<Artist> = dbQuery {
        ArtistTable
            .selectAll()
            .orderBy(ArtistTable.name to SortOrder.ASC)
            .map { it.toArtist() }
    }

    override suspend fun getArtistById(id: String): Artist = dbQuery {
        try {
            val uuid = UUID.fromString(id)

            ArtistTable
                .selectAll()
                .where { ArtistTable.id eq uuid }
                .map { it.toArtist() }
                .singleOrNull()
        } catch (e: Exception) {
            throw e
        } catch (e: NoSuchElementException) {
            throw ArtistNotFoundException("Artista no encontrado: $id")
        }
        as Artist
    }

    override suspend fun updateArtist(
        id: String,
        artist: Artist
    ): Artist = dbQuery {
        try {
            val uuid = UUID.fromString(id)

            val exists = ArtistTable
                .selectAll()
                .where { ArtistTable.id eq uuid }
                .singleOrNull()
                ?: throw NoSuchElementException("Artista no encontrado: $id")

            val now = Instant.now()

            ArtistTable.update({ ArtistTable.id eq uuid }) {
                artist.name.let { name -> it[ArtistTable.name] = name }
                it[genre] = artist.genre
                it[updatedAt] = now
            }

            ArtistTable
                .selectAll()
                .where { ArtistTable.id eq uuid }
                .map { it.toArtist() }
                .single()


        } catch (e: IllegalArgumentException) {
            throw ArtistNotFoundException("ID inválaido")
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteArtist(id: String): Boolean = dbQuery {
        try {
            val uuid = UUID.fromString(id)

            // Verificar que el artista existe
            ArtistTable
                .selectAll()
                .where { ArtistTable.id eq uuid}
                .singleOrNull()
                ?: throw NoSuchElementException("Artista no encontrado: $id")

            val deletedRows = ArtistTable.deleteWhere { ArtistTable.id eq uuid }
            deletedRows > 0
        } catch (e: Exception) {
            throw e
        } catch (e: IllegalArgumentException){
            throw ArtistNotFoundException("ID NO VÁLIDO O NO EXISTE")
        }
    }
}