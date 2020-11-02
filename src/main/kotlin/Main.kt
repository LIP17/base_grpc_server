import com.google.inject.Guice
import com.google.inject.Stage
import config.HealthServiceConfig
import io.vertx.core.Vertx
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modules.MainModule
import service.verticles.HealthServiceVerticle

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val injector = Guice.createInjector(Stage.PRODUCTION, MainModule())
        val vertx = injector.getInstance(Vertx::class.java)

        val deployments: MutableSet<String> = mutableSetOf()
        GlobalScope.launch(vertx.dispatcher()) {
            vertx.deployVerticleAwait(
                { injector.getInstance(HealthServiceVerticle::class.java) },
                HealthServiceConfig.deploymentOptions
            ).also { deployments.add(it) }
        }

        val grpcServer = injector.getInstance(GrpcServer::class.java)
        grpcServer.start()
        println("deploy finished")

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                grpcServer.stop()
                vertx.close()
                println("closed")
            }
        })
    }
}
