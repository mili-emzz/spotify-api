package infrastructure.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone
import org.jetbrains.exposed.sql.javatime.timestamp



object TrackTable: Table("tracks") {
    val id = uuid("id")
    val title = varchar("title", 150)
    val duration = integer("duration")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    val albumId = uuid("album_id").references(AlbumTable.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}