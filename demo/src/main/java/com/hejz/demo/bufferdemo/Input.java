package com.hejz.demo.bufferdemo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.InterruptibleChannel;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/3/10 14:40
 * 在 BIO 设计 中， 我们 从 InputStream 或 Reader 逐字 节 读取 数据。 假设 你 正在 处理 一个 基于 行的 文本 数据 流， 有如 下一 段 文本。
 */
public class Input {

    public static void main(String[] args) throws IOException {
//        bio();
        nio();
    }

    private static void bio() throws IOException {
        InputStream inputStream=new FileInputStream("d://info.txt");
        BufferedReader read = new BufferedReader(new InputStreamReader(inputStream));
        while (read.ready()){
            System.out.println(read.readLine());
        }
    }

    private static void nio() throws IOException {
        FileOutputStream FileOutputStream = new FileOutputStream("d://info.txt");
        FileChannel inChannel = FileOutputStream.getChannel();
        ByteBuffer buffer=ByteBuffer.allocate(48);
        int read = inChannel.read(buffer);
        while (read>0){
            System.out.println(read);
            read = inChannel.read(buffer);
            System.out.println(read);
        }
    }



}
