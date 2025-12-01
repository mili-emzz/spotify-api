package routes

import application.dto.TrackRequest
import application.dto.UpdateTrackRequest
import configuration.di.AppModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.trackRoutes(appModule: AppModule) {

    route("/tracks") {

        // POST /tracks
        post {
            try {
                val request = call.receive<TrackRequest>()

                if (request.title.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El título es requerido"))
                    return@post
                }

                if (request.duration <= 0) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "La duración debe ser mayor a 0"))
                    return@post
                }

                val response = appModule.createTrackUseCase.execute(request)
                call.respond(HttpStatusCode.Created, response)

            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear track: ${e.message}")
                )
            }
        }

        // GET /tracks
        get {
            try {
                val tracks = appModule.getAllTracksUseCase.execute()
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener tracks: ${e.message}")
                )
            }
        }

        // GET /tracks/{id}
        get("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val response = appModule.getTrackByIdUseCase.getTrackById(id)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener track: ${e.message}")
                )
            }
        }

        // PUT /tracks/{id}
        put("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val request = call.receive<UpdateTrackRequest>()

                if (request.title == null && request.duration == null) {
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

                if (request.duration != null && request.duration <= 0) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "La duración debe ser mayor a 0"))
                    return@put
                }

                val response = appModule.updateTrackUseCase.execute(id, request)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar track: ${e.message}")
                )
            }
        }

        // DELETE /tracks/{id}
        delete("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val deleted = appModule.deleteTrackUseCase.execute(id)

                if (deleted) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Track eliminado exitosamente"))
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to "No se pudo eliminar el track")
                    )
                }

            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar track: ${e.message}")
                )
            }
        }
    }

    // GET /albumes/{albumId}/tracks
    route("/albumes/{albumId}/tracks") {
        get {
            try {
                val albumId = call.parameters["albumId"]
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Album ID requerido")
                    )

                val response = appModule.getTracksByAlbumUseCase.getTrackByIdAlbum(albumId)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener tracks del álbum: ${e.message}")
                )
            }
        }
    }
}