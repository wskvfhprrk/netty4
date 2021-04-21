package com.hejz.demo.bufferdemo;

import java.nio.ByteBuffer;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/7 16:11
 * 手协分配缓冲区
 */
public class BufferWrap {
    public void myMethod(){
        //分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //包装一个现有的数组
        byte[] bytes = new byte[10];
        ByteBuffer buffer1 = ByteBuffer.wrap(bytes);
    }
}
