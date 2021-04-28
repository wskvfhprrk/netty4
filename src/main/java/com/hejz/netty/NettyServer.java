package com.hejz.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //创建两个group组
        // 处理连接请求的
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理监听事件组，进行业务处理的
        EventLoopGroup worderGroup = new NioEventLoopGroup(8);
        try {
            //创建服务端启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来配置参数
            bootstrap.group(bossGroup, worderGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception { //创建通道初始化对象，设置初始化参数
                    ch.pipeline().addLast(new NettyServerHandler()); //worderGroup设置前置处理器
                }
            });
            //使用NioServerSocketChannel做为异步的服务器端TCPSocket连接。
            //初始化服务器连接队列大小，服务端处理客户端连接请求是顺序处理的,所以同一时间只能处理一个客户端连接。
            // 多个客户端同时来的时候,服务端将不能处理的客户端连接请求放在队列中等待处理
            System.out.println("netty server start ...");
            //绑定一个端口并且同步,生成了一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            // 启动服务器(并绑定端口)，bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture cf = bootstrap.bind(9000).channel().closeFuture().sync();
            //给cf注册监听器，监听我们关心的事件
            cf.addListener((ChannelFutureListener) channelFuture -> {
                if (cf.isSuccess()) {
                    System.out.println("监听端口9000成功");
                } else {
                    System.out.println("监听端口9000失败");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            worderGroup.shutdownGracefully();
        }

    }

}
