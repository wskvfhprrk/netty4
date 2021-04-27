package com.hejz.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDate;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {
    //全局事件执行器，是单例模式
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + "上线了,"+ LocalDate.now()+"\n");
        //把当前channel放到执行器中
        channelGroup.add(channel);
        System.out.println("===="+ctx.channel().remoteAddress()+"，上线了===");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch->{
            if(channel!=ch){
                ch.writeAndFlush("[客户端]"+channel.remoteAddress()+"发送了消息："+msg+"\n");
            }else {
                ch.writeAndFlush("[自己]"+channel.remoteAddress()+"发送了消息："+msg+"\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
