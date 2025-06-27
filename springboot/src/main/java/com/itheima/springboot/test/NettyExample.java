package com.itheima.springboot.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * //在D:\develop\Java\jdk1.8.0_131\bin下进行编译
 *
 *
 * //先编译，
 * ./javac -d . -classpath ".;./netty-all-4.1.43.Final.jar;./netty-common-4.1.29.Final.jar" Example.java
 * //后执行,正常执行
 * ./java -classpath ".;./netty-all-4.1.43.Final.jar;./netty-common-4.1.29.Final.jar" com/itheima/springboot/test/Example
 *
 *
 * //先编译，
 *   ./javac -d . -classpath ".;./netty-common-4.1.29.Final.jar;./netty-all-4.1.43.Final.jar" Example.java
 * //后执行,报错    java.lang.NoSuchMethodError:
 *  // io.netty.util.concurrent.SingleThreadEventExecutor.<init>(Lio/netty/util/concurrent/EventExecutorGroup;
 *  //  Ljava/util/concurrent/Executor;ZLjava/util/Queue;Lio/netty/util/concurrent/RejectedExecutionHandler;)V
 * ./java -classpath ".;./netty-common-4.1.29.Final.jar;./netty-all-4.1.43.Final.jar" com/itheima/springboot/test/Example
 */
public class NettyExample {

    public static void main(String[] args) throws Exception {
        //
        Class<?> aClass = Class.forName("io.netty.util.concurrent.SingleThreadEventExecutor");

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        EventLoopGroup businessGroup = new NioEventLoopGroup(8);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ChannelPipeline channelPipeline = ch.pipeline();

                            channelPipeline.addLast(new StringEncoder());
                            channelPipeline.addLast(new StringDecoder());
                            channelPipeline.addLast("idleEventHandler", new IdleStateHandler(0, 10, 0));
                            channelPipeline.addAfter("idleEventHandler","loggingHandler",new LoggingHandler(LogLevel.INFO));
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}