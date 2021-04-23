package com.hejz.demo.bufferdemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/7 11:30
 */
public class BufferDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(("d://info.txt"));
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(10);
        output("初始化",buffer);
        channel.read(buffer);
        output("调用read()",buffer);
        buffer.flip();

        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            output("调用get()",buffer);
//            System.out.println((char) b);
        }
        buffer.clear();
        output("调用clear()",buffer);
        fileInputStream.close();
    }

    public static void output(String step, Buffer buffer) {
        System.out.println(step + ":");
        System.out.print("capaycity:" + buffer.capacity() + ",");
        System.out.print("position:" + buffer.position() + ",");
        System.out.println("limit:" + buffer.limit());
        System.out.println();
    }
}
