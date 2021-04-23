package com.hejz.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ScketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9000);
        while (true){
            System.out.println("服务端已经准备好连接。。。。");
            Socket socket = serverSocket.accept();
            System.out.printf("客户端连接了》》》》");
            heandler(socket);
        }
    }

    private static void heandler(Socket socket) throws IOException {
        byte[] bytes=new byte[1024];
        System.out.printf("准备read>>>");
        int read = socket.getInputStream().read(bytes);
        System.out.println("read准备完毕》》》");
        if(read!=-1){
            //把字节转成String 从0到N变成String
            System.out.printf("接收到客户端的信息：%s%n", new String(bytes,0,read));
        }
    }
}
