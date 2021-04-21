package com.hejz.demo.bufferdemo;

import java.nio.ByteBuffer;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/7 16:55
 * 只读缓冲区
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //缓冲区中的数据0-9
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        //创建只读缓冲区
        ByteBuffer readOnly = buffer.asReadOnlyBuffer();

        //改变原缓冲区的内容
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b*=10;
            buffer.put(i,b);
            //如果 尝试 修改 只读 缓冲区 的 内容， 则 会 报 ReadOnlyBufferException 异常。
//            readOnly.put(i,b);
        }
        readOnly.position(0);
        readOnly.limit(buffer.capacity());

        //只读缓冲区的内容也随之改变
        while (readOnly.remaining()>0){
            System.out.println(readOnly.get());
        }
    }
}
