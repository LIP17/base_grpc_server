package service.grpc

import com.google.protobuf.BoolValue
import lip17.base_grpc_service.BaseGrpcServiceCoroutineGrpc
import lip17.base_grpc_service.HealthRequest
import lip17.base_grpc_service.HealthResponse
import proxy.HealthService
import service.impl.isAliveAwait

class ServiceServer(
    private val healthService: HealthService
): BaseGrpcServiceCoroutineGrpc.BaseGrpcServiceImplBase() {

    override suspend fun healthStatus(request: HealthRequest): HealthResponse {
        println("${ServiceServer::class.java}:${Thread.currentThread()}")

        val isHealthy = BoolValue.of(healthService.isAliveAwait())
        return HealthResponse.newBuilder().apply {
            this.pongMsg = request.pingMsg
            this.isHealthy = isHealthy
        }.build()
    }
}
