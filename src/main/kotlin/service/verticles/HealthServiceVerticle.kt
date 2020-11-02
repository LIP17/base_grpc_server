package service.verticles

import config.HealthServiceConfig
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.serviceproxy.ServiceBinder
import proxy.HealthService

class HealthServiceVerticle(
    private val healthService: HealthService
): CoroutineVerticle() {

    override suspend fun start() {
        val binder = ServiceBinder(vertx)

        binder.setAddress(HealthServiceConfig.eventBusTopic())
            .register(HealthService::class.java, healthService)
    }

    override suspend fun stop() {
        println("$deploymentID stopped.")
    }

}
