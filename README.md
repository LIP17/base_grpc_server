# Vertx Working with gRPC

An integration with both gRPC and Vert.x, notice here we do not use \
Vert.x gRPC dependency because due to Netty version incompatibility, Vert.x \
gRPC server does not use Vert.x EventLoopThreadPool, so everytime a request coming \
and hit next Verticle will require 2 thread switch, which is not ideal.
