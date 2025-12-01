package routes

import application.dto.ArtistRequest
import application.dto.UpdateArtistRequest
import configuration.di.AppModule
import domain.exceptions.ArtistNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.artistRoutes(appModule: AppModule) {

    route("/artistas") {

        post {
            try {
                val request = call.receive<ArtistRequest>()

                if (request.name.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El nombre es requerido"))
                    return@post
                }

                if (request.genre.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El género es requerido"))
                    return@post
                }

                val response = appModule.createArtistUseCase.execute(request)
                call.respond(HttpStatusCode.Created, response)

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear artista: ${e.message}")
                )
            }
        }

        get {
            try {
                val artists = appModule.getAllArtistsUseCase.execute()
                call.respond(HttpStatusCode.OK, artists)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener artistas: ${e.message}")
                )
            }
        }

        // GET /artistas/{id} - Obtener artista por ID
        get("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val response = appModule.getArtistByIdUseCase.execute(id)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: ArtistNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener artista: ${e.message}")
                )
            }
        }

        put("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val request = call.receive<UpdateArtistRequest>()

                if (request.name == null && request.genre == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Debe proporcionar al menos un campo para actualizar")
                    )
                    return@put
                }

                if (request.name != null && request.name.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El nombre no puede estar vacío"))
                    return@put
                }

                if (request.genre != null && request.genre.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El género no puede estar vacío"))
                    return@put
                }

                val response = appModule.updateArtistUseCase.execute(id, request)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: ArtistNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar artista: ${e.message}")
                )
            }
        }

        delete("/{id}") {
            try {
                val id = call.parameters["id"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID requerido"))

                val deleted = appModule.deleteArtistUseCase.execute(id)

                if (deleted) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Artista eliminado exitosamente"))
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to "No se pudo eliminar el artista")
                    )
                }

            } catch (e: ArtistNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar artista: ${e.message}")
                )
            }
        }
    }
}