package com.hejz.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入解码器
                            pipeline.addLast(new StringDecoder());
                            //加入编码器
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("服务端启动");
            ChannelFuture cf = bootstrap.bind(9000).channel().closeFuture().sync();
            //给cf注册监听器，监听我们关心的事件
//            cf.addListener((ChannelFutureListener) channelFuture -> {
//                if (cf.isSuccess()) {
//                    System.out.println("监听端口9000成功");
//                } else {
//                    System.out.println("监听端口9000失败");
//                }
//            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
