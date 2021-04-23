package com.hejz.demo.bufferdemo;

import java.nio.IntBuffer;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/2/2 17:27
 */
public class IntBufferDemo {
    public static void main(String[] args) {
        //分配缓冲区，参数为缓冲区容量
        IntBuffer intBuffer = IntBuffer.allocate(8);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            int j=2*(i+1);
            //将给定整数写入此缓冲区的当前位置，当前位置递增
            intBuffer.put(j);
        }
        //重设此缓冲区，将限制位置为当前位置，然后将当前位置设置为0
        intBuffer.flip();
        //当前位置和限制位置之间是否有元素
        while (intBuffer.hasRemaining()){
            //读取此缓冲区当前位置的整数，然后当前位置递增
            int j= intBuffer.get();
            System.out.print(j+" ");
        }
    }
}
