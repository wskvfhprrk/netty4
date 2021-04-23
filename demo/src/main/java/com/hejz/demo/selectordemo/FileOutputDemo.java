package com.hejz.demo.selectordemo;

import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/8 9:46
 */
public class FileOutputDemo {
    private static final byte message[]={83,111,109,101,32,98,121,116,101,115,46};

    public static void main(String[] args) throws Exception{
        FileOutputStream fileOutputStream = new FileOutputStream("D://test.txt");
        FileChannel fc = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < message.length; i++) {
            buffer.put(message[i]);
        }
        buffer.flip();
        fc.write(buffer);
        fileOutputStream.close();
    }
}
