package service.impl

import com.google.inject.Singleton
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.kotlin.coroutines.awaitResult
import proxy.HealthService

@Singleton
class HealthServiceImpl: HealthService {
    override fun isAlive(handler: Handler<AsyncResult<Boolean>>): HealthService {
        handler.handle(Future.succeededFuture(true))
        return this
    }
}

suspend fun HealthService.isAliveAwait(): Boolean {
    return awaitResult(::isAlive)
}