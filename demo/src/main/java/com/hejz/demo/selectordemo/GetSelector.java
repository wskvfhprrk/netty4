package com.hejz.demo.selectordemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * selector
 * @author hejz
 * @version 1.0
 * @date 2021/4/8 8:47
 */
public class GetSelector {
    private int prot;

    //注册事件
    private Selector getSelector() throws IOException{
        //创建selector
        Selector selector = Selector.open();
        //创建可选择通道，并配置为非阻塞模式
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        //绑定通道到指定端口
        ServerSocket socket = server.socket();
        InetSocketAddress address = new InetSocketAddress(prot);
        socket.bind(address);
        //向selector注册感兴趣的事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }
    //根据不同的事件做处理
    private  void process(SelectionKey key) throws IOException{
        Selector selector = getSelector();
        //接受请求
        if(key.isAcceptable()){
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector,SelectionKey.OP_READ);
        }
        //读数据
        else if(key.isReadable()){
            SocketChannel channel = (SocketChannel) key.channel();
//            channel.read(buffer);
        }

    }
}
