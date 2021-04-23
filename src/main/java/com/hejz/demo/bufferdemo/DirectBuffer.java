package com.hejz.demo.bufferdemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/7 17:20
 * 直接缓冲区
 */
public class DirectBuffer {
    public static void main(String[] args) throws Exception{
        //从磁盘上读取文件内容
        String inFile="D://info.txt";
        FileInputStream fileInputStream = new FileInputStream(inFile);
        FileChannel fcin = fileInputStream.getChannel();

        //把读取的内容写入一个新的文件
        String outFile = String.format("D://test.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        FileChannel fcon = fileOutputStream.getChannel();

        //使用allocateDirect而不是allocate
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true){
            buffer.clear();
            int r = fcin.read(buffer);
            if(r==-1){
                break;
            }
            buffer.flip();
            fcon.write(buffer);
        }
    }
}
