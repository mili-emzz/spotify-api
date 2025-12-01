package infrastructure.database.tables
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone

object AlbumTable: Table("albumes"){
    val id = uuid("id")
    val title = varchar("title", 150)
    val releaseYear = integer("release_year")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    val artistId = uuid("artist_id").references(ArtistTable.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}