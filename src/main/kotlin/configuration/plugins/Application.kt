import com.emiliagomez.configuration.configureRouting
import com.emiliagomez.configuration.configureSerialization
import configuration.di.AppModule
import configuration.plugins.configureMonitoring
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting(AppModule())
    configureMonitoring()
}