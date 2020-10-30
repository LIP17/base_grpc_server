import config.HealthServiceConfig
import io.grpc.netty.NettyServerBuilder
import io.vertx.core.Vertx
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import service.factory.HealthServiceFactory
import grpc.ServiceServer
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()

        GlobalScope.launch(vertx.dispatcher()) {
            vertx.deployVerticleAwait(
                HealthServiceConfig.verticleCanonicalName,
                HealthServiceConfig.deploymentOptions
            )
        }

        val serviceProxy = HealthServiceFactory.createProxy(vertx, HealthServiceConfig.eventBusTopic())

        val grpcServer = NettyServerBuilder
            .forPort(50000)
            .executor(ThreadPoolExecutor(16, 32, 60, TimeUnit.SECONDS, LinkedBlockingQueue()))
            .addService(ServiceServer(serviceProxy))
            .build()

        grpcServer.start()
        println("deploy finished")

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                vertx.close()
                println("closed")
            }
        })
    }
}
