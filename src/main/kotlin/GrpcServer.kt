import com.google.inject.Inject
import com.google.inject.Singleton
import io.grpc.BindableService
import io.grpc.Server
import io.grpc.netty.NettyServerBuilder
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Singleton
class GrpcServer {

    private val server: Server

    @Inject
    constructor(
        grpcServices: java.util.Set<BindableService>
    ) {

        val serverBuilder = NettyServerBuilder
            .forPort(50000)
            .executor(ThreadPoolExecutor(16, 32, 60, TimeUnit.SECONDS, LinkedBlockingQueue()))

        grpcServices.forEach { serverBuilder.addService(it) }

        server = serverBuilder.build()
    }

    fun start() {
        server.start()
    }

    fun stop() {
        server.shutdown()
    }
}