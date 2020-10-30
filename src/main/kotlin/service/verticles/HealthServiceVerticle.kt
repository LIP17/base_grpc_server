package service.verticles

import config.HealthServiceConfig
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.serviceproxy.ServiceBinder
import proxy.HealthService
import service.factory.HealthServiceFactory

class HealthServiceVerticle: CoroutineVerticle() {

    override suspend fun start() {
        val healthServiceImpl = HealthServiceFactory.createImpl()
        val binder = ServiceBinder(vertx)

        binder.setAddress(HealthServiceConfig.eventBusTopic())
            .register(HealthService::class.java, healthServiceImpl)
    }

}
