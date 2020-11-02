package service.factory

import proxy.HealthService
import service.impl.HealthServiceImpl

object HealthServiceFactory {

    fun createImpl(): HealthService {
        return HealthServiceImpl()
    }
}