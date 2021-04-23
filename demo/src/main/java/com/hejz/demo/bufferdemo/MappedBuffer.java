package com.hejz.demo.bufferdemo;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * I/O映射缓冲区
 * @author hejz
 * @version 1.0
 * @date 2021/4/7 17:45
 */
public class MappedBuffer {
    static  private  final int start=0;
    static private final int size=1024;

    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("D://info.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        //把缓冲区跟文件系统进行一个映射关联
        //只要操作缓冲区里面的内容，文件内容也会跟着改变
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, start, size);
        map.put(0,(byte)97);
        map.put(1023,(byte) 122);
        randomAccessFile.close();
    }
}
