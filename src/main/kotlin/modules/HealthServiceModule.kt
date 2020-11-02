package modules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import config.HealthServiceConfig
import io.vertx.core.Vertx
import proxy.HealthService
import proxy.HealthServiceVertxEBProxy
import service.impl.HealthServiceImpl

class HealthServiceModule: AbstractModule() {
    @Provides
    @Singleton
    @Named("health-service-impl")
    fun getHealthServiceImpl(): HealthService {
        return HealthServiceImpl()
    }

    @Provides
    @Singleton
    @Named("health-service-proxy")
    fun getHealthServiceProxy(vertx: Vertx): HealthService {
        return HealthServiceVertxEBProxy(vertx, HealthServiceConfig.eventBusTopic())
    }
}