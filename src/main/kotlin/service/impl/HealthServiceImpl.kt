package service.impl

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.kotlin.coroutines.awaitResult
import proxy.HealthService

class HealthServiceImpl: HealthService {
    override fun isAlive(handler: Handler<AsyncResult<Boolean>>): HealthService {
        println("${HealthServiceImpl::class.java}:${Thread.currentThread()}")
        handler.handle(Future.succeededFuture(true))
        return this
    }
}

suspend fun HealthService.isAliveAwait(): Boolean {
    return awaitResult(::isAlive)
}