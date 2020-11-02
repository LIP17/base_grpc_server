package modules

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.multibindings.ProvidesIntoSet
import grpc.BaseGrpcServiceImpl
import io.grpc.BindableService

class GrpcServiceModule: AbstractModule() {
    @ProvidesIntoSet
    @Singleton
    fun getBaseGrpcService(impl: BaseGrpcServiceImpl): BindableService {
        return impl
    }
}