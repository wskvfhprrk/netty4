package com.hejz.tomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/9 9:29
 */
public class GPResponse {
    private OutputStream out;
    private ChannelHandlerContext ctx;
    private HttpRequest req;

    public GPResponse(OutputStream out) {
        this.out = out;
    }

    public GPResponse(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public void write(String s) throws Exception {
        try {
            if (s == null || s.length() == 0) {
                return;
            }
            //设置HTTP及请求头信息
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    //设置版本HTTP/1.1
                    HttpVersion.HTTP_1_0,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(s.getBytes("UTF-8"))
            );
            response.headers().set("content-Type", "text/html");
            ctx.write(response);
//        StringBuilder sb = new StringBuilder();
//        sb.append("HTTP/1.1 200 OK\n")
//                .append("content-Type:text/html;\n")
//                .append("\r\n")
//                .append(s);
//        out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
