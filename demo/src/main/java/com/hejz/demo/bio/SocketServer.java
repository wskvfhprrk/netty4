package com.hejz.demo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9000);
        System.out.println("等待连接。。。。。");
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("有客户连接了。。。。。");
            handler(socket);
        }
    }

    private static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read.......");
        int read = socket.getInputStream().read(bytes);
        if(read!=-1){
            System.out.println("收到客户端的数据："+new String(bytes,0,read));
        }
    }
}
