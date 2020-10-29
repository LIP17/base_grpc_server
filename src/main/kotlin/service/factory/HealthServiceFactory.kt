package service.factory

import io.vertx.core.Vertx
import proxy.HealthService
import proxy.HealthServiceVertxEBProxy
import service.impl.HealthServiceImpl

object HealthServiceFactory {

    fun createImpl(): HealthService {
        return HealthServiceImpl()
    }

    fun createProxy(vertx: Vertx, address: String): HealthService {
        return HealthServiceVertxEBProxy(vertx, address)
    }

}