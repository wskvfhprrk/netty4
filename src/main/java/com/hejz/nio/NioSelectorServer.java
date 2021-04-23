package com.hejz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 使用多路复用器的nio服务
 */
public class NioSelectorServer {

    public static void main(String[] args) throws IOException {
        //创建NIO serverSocketChannel，与BIO的serversocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //打开Selector多路复用器——提高性能，不使用集合能提高性能
        Selector selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");
        while (true) {
            //阻塞处理发生的事件
            selector.select();
            //获取全部事例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //如果是OP_ACCEPT的key，就去selector注册为OP_READ
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    //把其标记并注册到selector中。
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                    //如果是读OP_READ状态的key就读取数据进行处理
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int read = socketChannel.read(byteBuffer);
                    //如果有数据进入数据处理
                    if (read > 0) {
                        System.out.println("接收到消息：" + new String(byteBuffer.array()));
                        //如果是断开连接，关闭socketChanel
                    } else if (read == -1) {
                        System.out.println("客户端断开连接。");
                        socketChannel.close();
                    }

                }
            }
            iterator.remove();
        }
    }
}