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

public class NioSelectorServer {

    public static void main(String[] args) throws IOException {
        //创建NIO serverSocketChannel，与BIO的serversocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //打开Selector多路复用器
        Selector selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");
        while (true) {
            //阻塞处理发生的事件——仅处理有事件发生的事件，其它不的处理
            selector.select();
            //获取全部事例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int read = socketChannel.read(byteBuffer);
                    if (read > 0) {
                        System.out.println("接收到消息：" + new String(byteBuffer.array()));
                    } else if (read == -1) {
                        System.out.println("客户端断开连接。");
                        channel.close();
                    }

                }
            }
            iterator.remove();
        }
    }
}