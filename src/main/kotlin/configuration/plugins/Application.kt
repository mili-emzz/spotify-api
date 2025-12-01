import com.emiliagomez.configuration.configureRouting
import com.emiliagomez.configuration.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting(AppModule())
    configureMonitoring()
}
