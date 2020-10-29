import config.GrpcServerConfig
import config.HealthServiceConfig
import io.vertx.core.Vertx
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()

        GlobalScope.launch(vertx.dispatcher()) {
            vertx.deployVerticleAwait(
                GrpcServerConfig.verticleCanonicalName,
                GrpcServerConfig.deploymentOptions
            )

            vertx.deployVerticleAwait(
                HealthServiceConfig.verticleCanonicalName,
                HealthServiceConfig.deploymentOptions
            )
        }

        println("deploy finished")

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                vertx.close()
                println("closed")
            }
        })
    }
}
