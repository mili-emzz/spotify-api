package infrastructure.adapters.out

import DatabaseFactory.dbQuery
import com.emiliagomez.domain.models.Album
import domain.exceptions.AlbumNotFoundException
import domain.ports.AlbumRepository
import infrastructure.database.tables.AlbumTable
import infrastructure.database.tables.ArtistTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.Instant
import java.util.UUID

class ExposedAlbumRepo : AlbumRepository {

    private fun ResultRow.toAlbum() = Album(
        id = this[AlbumTable.id],
        artistId = this[AlbumTable.artistId],
        title = this[AlbumTable.title],
        releaseYear = this[AlbumTable.releaseYear],
        createdAt = this[AlbumTable.createdAt],
        updatedAt = this[AlbumTable.updatedAt]
    )

    override suspend fun createAlbum(album: Album): Album = dbQuery {
        try {
            // Verificar que el artista existe
            ArtistTable
                .selectAll()
                .where { ArtistTable.id eq album.artistId }
                .singleOrNull()
                ?: throw NoSuchElementException("Artista no encontrado: ${album.artistId}")

            val now = Instant.now()

            AlbumTable.insert {
                it[id] = album.id
                it[artistId] = album.artistId
                it[title] = album.title
                it[releaseYear] = album.releaseYear
                it[createdAt] = now
                it[updatedAt] = now
            }

            album.copy(
                createdAt = now,
                updatedAt = now
            )
        } catch (e: Exception) {
            throw e
        } catch (e: IllegalArgumentException){
            throw AlbumNotFoundException("Artista no encontrado: ${album.artistId}")
        }
    }

    override suspend fun getAllAlbums(): List<Album> = dbQuery {
        AlbumTable
            .selectAll()
            .orderBy(AlbumTable.releaseYear to SortOrder.DESC)
            .map { it.toAlbum() }
    }

    override suspend fun getAlbumById(id: String): Album = dbQuery {
        try {
            AlbumTable
                .selectAll()
                .where { AlbumTable.id eq UUID.fromString(id) }
                .map { it.toAlbum() }
                .singleOrNull()
                ?: throw NoSuchElementException("Álbum no encontrado: $id")
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("ID inválido: $id no es un UUID válido")
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAlbumsByArtistId(artistId: String): List<Album> = dbQuery {
        try {
            val uuid = UUID.fromString(artistId)
            // Verificar que el artista existe
            ArtistTable
                .selectAll()
                .where { ArtistTable.id eq uuid }
                .singleOrNull()
                ?: throw NoSuchElementException("Artista no encontrado: $artistId")

            AlbumTable
                .selectAll()
                .where { AlbumTable.artistId eq uuid }
                .orderBy(AlbumTable.releaseYear to SortOrder.DESC)
                .map { it.toAlbum() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateAlbum(
        id: String,
        album: Album
    ): Album = dbQuery {
        try {
            val uuid = UUID.fromString(id)

            val exists = AlbumTable
                .selectAll()
                .where { AlbumTable.id eq uuid }
                .singleOrNull()
                ?: throw NoSuchElementException("Álbum no encontrado: $id")

            val now = Instant.now()

            AlbumTable.update({ AlbumTable.id eq uuid }) {
                it[title] = album.title
                it[releaseYear] = album.releaseYear
                it[updatedAt] = Instant.now()
            }

            AlbumTable
                .selectAll()
                .where { AlbumTable.id eq uuid }
                .map { it.toAlbum() }
                .single()

        } catch (e: Exception) {
            throw e
        } catch (e: IllegalArgumentException){
            throw AlbumNotFoundException("ID inválido $id no es UUID")
        }
    }

    override suspend fun deleteAlbum(id: String): Boolean = dbQuery {
        try {
            val uuid = UUID.fromString(id)

            // Verificar que el album existe
            AlbumTable
                .selectAll()
                .where { ArtistTable.id eq uuid}
                .singleOrNull()
                ?: throw NoSuchElementException("Album no encontrado: $id")

            val deletedRows = ArtistTable.deleteWhere { AlbumTable.id eq uuid }
            deletedRows > 0
        } catch (e: Exception) {
            throw e
        } catch (e: IllegalArgumentException){
            throw AlbumNotFoundException("ID NO VÁLIDO O NO EXISTE")
        }
    }
}