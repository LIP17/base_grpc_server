package service.verticles

import config.HealthServiceConfig
import io.vertx.grpc.VertxServerBuilder
import io.vertx.kotlin.coroutines.CoroutineVerticle
import proxy.HealthService
import service.factory.HealthServiceFactory
import service.grpc.ServiceServer

class GrpcServerVerticle: CoroutineVerticle() {

    private lateinit var healthServie: HealthService

    override suspend fun start() {
        healthServie = HealthServiceFactory.createProxy(vertx, HealthServiceConfig.eventBusTopic())


        println("gprc verticle start ${Thread.currentThread()}")

        val vertxServer = VertxServerBuilder
            .forAddress(vertx, "localhost", 50000)
            .addService(ServiceServer(healthServie))
            .build()
        vertxServer.start()
    }
}