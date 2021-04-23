package com.hejz.demo.bufferdemo;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/7 11:18
 */
public class AioServer {
    private final int port;

    public AioServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port=8080;
        new AioServer(port);
    }
}
