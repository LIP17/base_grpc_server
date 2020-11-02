package modules

import com.google.inject.AbstractModule

class MainModule: AbstractModule() {

    override fun configure() {
        install(GrpcServiceModule())
        install(HealthServiceModule())
        install(VertxModule())
    }
}