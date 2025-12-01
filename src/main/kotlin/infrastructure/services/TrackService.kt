package infrastructure.services

import DatabaseFactory.dbQuery
import com.emiliagomez.domain.models.Track
import domain.exceptions.TrackNotFoundException
import domain.ports.TracksRepository
import infrastructure.database.tables.AlbumTable
import infrastructure.database.tables.TrackTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.util.UUID

class TrackService: TracksRepository {

    private fun ResultRow.toTrack() = Track (
        id = this[TrackTable.id],
        title = this[TrackTable.title],
        duration = this[TrackTable.duration],
        albumId = this[TrackTable.albumId],
        createdAt = this[TrackTable.createdAt],
        updatedAt = this[TrackTable.updatedAt]
    )

    override suspend fun createTrack(track: Track): Track = dbQuery {
        try{
            AlbumTable
                .selectAll()
                .where { AlbumTable.id eq track.albumId }
                .singleOrNull()
                ?: throw NoSuchElementException("Album no encontrado: ${track.albumId}")

            val now = Instant.now()

            TrackTable.insert {
                it[id] = track.id
                it[albumId] = track.albumId
                it[title] = track.title
                it[duration] = track.duration
                it[createdAt] = now
                it[updatedAt] = now
            }

            track.copy(
                createdAt = now,
                updatedAt = now
            )

        } catch(e: Exception){
            throw e
        } catch(e: IllegalArgumentException){
            throw TrackNotFoundException("Cancion no encontrada: ${track.title}")
        }
    }

    override suspend fun getAllTracks(): List<Track> = dbQuery {
        TrackTable
            .selectAll()
            .orderBy(TrackTable.title to SortOrder.ASC)
            .map { it.toTrack() }
    }

    override suspend fun getTrackById(id: String): Track? = dbQuery{
        try {

            val uuid = UUID.fromString(id)

            TrackTable
                .selectAll()
                .where { TrackTable.id eq uuid }
                .map { it.toTrack() }
                .singleOrNull()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTracksByAlbumId(albumId: String): List<Track> = dbQuery {
        try{
            val uuid = UUID.fromString(albumId)


            AlbumTable
                .selectAll()
                .where { AlbumTable.id eq uuid }
                .singleOrNull()
                ?: throw NoSuchElementException("Álbum no encontrado: $albumId")

            TrackTable
                .selectAll()
                .where { TrackTable.albumId eq uuid }
                .orderBy(TrackTable.id to SortOrder.ASC)
                .map { it.toTrack() }

        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("ID de álbum inválido: $albumId no es un UUID válido")
        } catch (e: Exception) {
            throw e

        }
    }

    override suspend fun updateTrack(
        id: String,
        track: Track
    ): Track = dbQuery {
        try{
            val uuid = UUID.fromString(id)

            TrackTable
                .selectAll()
                .where{ TrackTable.id eq uuid }
                .singleOrNull()
                ?: throw NoSuchElementException("Track no encontrado: $id")


            val now = Instant.now()

            TrackTable.update({ TrackTable.id eq uuid }) {
                it[title] = track.title
                it[duration] = track.duration
                it[updatedAt] = now
            }

            TrackTable
                .selectAll()
                .where { TrackTable.id eq uuid }
                .map { it.toTrack() }
                .single()
        }catch (e: IllegalArgumentException){
            throw TrackNotFoundException("ID: $id not found: ${e.message}")
        } catch(e: Exception){
            throw e
        }
    }

    override suspend fun deleteTrack(id: String): Boolean = dbQuery {
        try {
            val uuid = UUID.fromString(id)


            TrackTable
                .selectAll()
                .where { TrackTable.id eq uuid }
                .singleOrNull()
                ?: throw NoSuchElementException("Track no encontrado: $id")

            val deletedRows = TrackTable.deleteWhere { TrackTable.id eq uuid }
            deletedRows > 0

        } catch (e: IllegalArgumentException) {
            throw TrackNotFoundException("ID inválido: $id no es un UUID válido")
        } catch (e: Exception) {
            throw e
        }
    }
}