package com.hejz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NioServer {
    //存放SocketChannel的集合——这样有一个坏处，无论多少连接都需要遍历查询有值的进行处理，使用selector——多路复用器来代替，并提高性能
    private static List<SocketChannel> chanelList=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //创建NIO serverSocketChannel，与BIO的serversocket类似
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //serverSocketChannel中的socket绑定端口地址
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //设置为非阻塞,
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
                    //移除里面为空值的socketChanel
                    iterator.remove();
                    System.out.println("客户端断开连接。");
                }

            }
        }
    }
}