package com.emiliagomez.api

import configuration.di.AppModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import routes.albumRoutes
import routes.artistRoutes
import routes.trackRoutes

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    install(ContentNegotiation) {
        json()
    }

    val appModule = AppModule()

    routing {
        route("/api") {
            artistRoutes(appModule)
            albumRoutes(appModule)
            trackRoutes(appModule)
        }
    }
}