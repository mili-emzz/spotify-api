package com.emiliagomez.configuration

import configuration.di.AppModule
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import routes.albumRoutes
import routes.artistRoutes
import routes.trackRoutes

fun Application.configureRouting(appModule: AppModule) {
    routing {
        route("/api") {
            artistRoutes(appModule)
            albumRoutes(appModule)
            trackRoutes(appModule)
        }
    }
}
