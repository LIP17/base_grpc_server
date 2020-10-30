package config

import io.vertx.core.DeploymentOptions
import proxy.HealthService
import service.verticles.HealthServiceVerticle


interface ConfigNaming {
    val prefix: String
}

interface VerticleConfig {
    val deploymentOptions: DeploymentOptions
    val verticleCanonicalName: String
}

abstract class ServiceConfig : ConfigNaming, VerticleConfig {
    override val prefix: String = "service"
    protected abstract val eventBusTopic: String

    fun eventBusTopic(): String {
        return "$prefix/$eventBusTopic"
    }
}

object HealthServiceConfig : ServiceConfig() {
    override val deploymentOptions: DeploymentOptions by lazy {
        DeploymentOptions().setInstances(2)
    }

    override val eventBusTopic: String = HealthService::class.java.simpleName
    override val verticleCanonicalName: String by lazy {
        HealthServiceVerticle::class.java.canonicalName
    }
}
