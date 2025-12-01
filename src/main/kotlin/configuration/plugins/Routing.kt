package com.emiliagomez.configuration

import configuration.di.AppModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import routes.albumRoutes
import routes.artistRoutes

fun Application.configureRouting() {

    val appModule = AppModule()
    routing {
        get("/") {
            call.respondText("NICE! NICE! Very nice")
        }

        get("/health"){
            call.respondText("OK")
        }

        artistRoutes(appModule)
        albumRoutes(appModule)
    }
}
