package routes

import application.dto.AlbumRequest
import application.dto.UpdateAlbumRequest
import configuration.di.AppModule
import domain.exceptions.AlbumNotFoundException
import domain.exceptions.ArtistNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.albumRoutes(appModule: AppModule) {

    route("/albumes") {

        post {
            try {
                val request = call.receive<AlbumRequest>()

                if (request.title.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El título es requerido"))
                    return@post
                }

                if (request.releaseYear !in 1900..2100) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Año de lanzamiento inválido"))
                    return@post
                }

                val response = appModule.createAlbumUseCase.execute(request)
                call.respond(HttpStatusCode.Created, response)

            } catch (e: ArtistNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear álbum: ${e.message}")
                )
            }
        }

        get {
            try {
                val albums = appModule.getAllAlbumsUseCase.execute()
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener álbumes: ${e.message}")
                )
            }
        }

        get("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val response = appModule.getAlbumByIdUseCase.execute(id)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: AlbumNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener álbum: ${e.message}")
                )
            }
        }

        put("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val request = call.receive<UpdateAlbumRequest>()

                if (request.title == null && request.releaseYear == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Debe proporcionar al menos un campo para actualizar")
                    )
                    return@put
                }

                if (request.title != null && request.title.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El título no puede estar vacío"))
                    return@put
                }

                if (request.releaseYear != null && request.releaseYear !in 1900..2100) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Año de lanzamiento inválido"))
                    return@put
                }

                val response = appModule.updateAlbumUseCase.execute(id, request)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: AlbumNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar álbum: ${e.message}")
                )
            }
        }

        delete("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val deleted = appModule.deleteAlbumUseCase.execute(id)

                if (deleted) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Álbum eliminado exitosamente"))
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to "No se pudo eliminar el álbum")
                    )
                }

            } catch (e: AlbumNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar álbum: ${e.message}")
                )
            }
        }
    }

    route("/artistas/{artistId}/albumes") {
        get {
            try {
                val artistId = call.parameters["artistId"]
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Artist ID requerido")
                    )

                val response = appModule.getAlbumsByArtistUseCase.execute(artistId)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: ArtistNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener álbumes del artista: ${e.message}")
                )
            }
        }
    }
}