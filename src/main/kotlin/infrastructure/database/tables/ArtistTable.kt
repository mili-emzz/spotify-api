package infrastructure.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp


object ArtistTable: Table("artistas") {
    val id = uuid("id")
    val name = varchar("name", 100)
    val genre = varchar("genre", 50)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    override val primaryKey = PrimaryKey(id)
}