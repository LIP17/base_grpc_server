package grpc

import com.google.inject.Inject
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.google.protobuf.BoolValue
import lip17.base_grpc_service.BaseGrpcServiceCoroutineGrpc
import lip17.base_grpc_service.HealthRequest
import lip17.base_grpc_service.HealthResponse
import proxy.HealthService
import service.impl.isAliveAwait

@Singleton
class BaseGrpcServiceImpl @Inject constructor(
    @Named("health-service-proxy") private val healthService: HealthService
) : BaseGrpcServiceCoroutineGrpc.BaseGrpcServiceImplBase() {

    override suspend fun healthStatus(request: HealthRequest): HealthResponse {
        val isHealthy = BoolValue.of(healthService.isAliveAwait())
        return HealthResponse.newBuilder().apply {
            this.pongMsg = request.pingMsg
            this.isHealthy = isHealthy
        }.build()
    }
}
