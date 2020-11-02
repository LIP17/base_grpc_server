package modules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import io.vertx.core.Vertx
import proxy.HealthService
import service.verticles.HealthServiceVerticle

class VertxModule: AbstractModule() {

    @Provides
    @Singleton
    fun getVertx(): Vertx {
        return Vertx.vertx()
    }

    @Provides
    fun getHealthServiceVerticle(@Named("health-service-impl") healthServiceImpl: HealthService): HealthServiceVerticle {
        return HealthServiceVerticle(healthServiceImpl)
    }
}