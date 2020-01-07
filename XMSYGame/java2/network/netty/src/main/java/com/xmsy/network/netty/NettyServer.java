package com.xmsy.network.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 开启Netty 服务
 */
public class NettyServer 
{
    public static void startSocketServer(int inetPort, ChannelInitializer<SocketChannel> childHandler) {
    	//服务端需要2个线程组  boss处理客户端连接  work进行客服端连接之后的处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //服务器 配置
            bootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                    .childHandler(childHandler).option(ChannelOption.SO_BACKLOG,1024).childOption(ChannelOption.SO_KEEPALIVE,true);
            //绑定端口  开启事件驱动
            System.out.println("【大厅服务器启动成功(游戏服务器连接)========端口："+ inetPort +"】");
            Channel channel = bootstrap.bind(inetPort).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
