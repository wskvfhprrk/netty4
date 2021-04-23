package com.hejz.nio;

import ch.qos.logback.core.BasicStatusManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NioServer {
    private static List<SocketChannel> chanelList=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //创建NIO serverSocketChannel，与BIO的serversocket类似
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务启动成功");
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                System.out.println("连接成功！");
                socketChannel.configureBlocking(false);
                chanelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = chanelList.iterator();
            while (iterator.hasNext()){
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                int read = sc.read(byteBuffer);
                if(read>0){
                    System.out.println("接收到消息："+new String(byteBuffer.array()));
                }else if(read==-1){
                    iterator.remove();
                    System.out.println("客户端断开连接。");
                }

            }
        }
    }
}