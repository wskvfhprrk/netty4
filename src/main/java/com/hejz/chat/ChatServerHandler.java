package com.hejz.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDate;

public class ChatServerHandler extends SimpleChannelInboundHandler {
    //全局事件执行器，是单例模式
    static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 连接后执行事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("【客户端】" + ctx.channel().remoteAddress() + "上线了,"+ LocalDate.now()+"\n");
        //把当前channel放到执行器中
//        channelGroup.add(ctx.channel());
        System.out.println("===="+ctx.channel().remoteAddress()+"，上线了===");
    }

    /**
     * 客户端发送过来数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : channelGroup) {
            if(channel!=ch){
                ch.writeAndFlush("[客户端]"+channel.remoteAddress()+"发送了消息："+msg+"\n");
            }else {
                ch.writeAndFlush("[自己]"+channel.remoteAddress()+"发送了消息："+msg+"\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
