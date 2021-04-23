package com.hejz.servlet;

import com.hejz.servlet.tomcat.GPServlet;
import com.hejz.tomcat.GPRequest;
import com.hejz.tomcat.GPResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * netty修改的tomcat
 * @author hejz
 * @version 1.0
 * @date 2021/4/9 15:56
 */
public class NetyTomcat {
    //打开tomcat源码，全局搜索ServerSocket
    private int port=8080;
    private Map<String, GPServlet> servletMapping=new HashMap<>();
    private Properties webxml=new Properties();
    private void init(){
        //加载web.xml文件，同时初始化servletMapping
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");
            webxml.load(fis);
            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMapping.put(url,obj);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start(){
        //1加载配置文件，初始化servletMapping
        init();
        //Netty封闭了NIO的Reactor模型
        //Boss线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //1、创建对象
            // Netty服务
            //ServetBootstrap   ServerSocketChannel
            ServerBootstrap server = new ServerBootstrap();


            //2、配置参数
            // 链路式编程
            server.group(bossGroup, workerGroup)
                    // 主线程处理类,看到这样的写法，底层就是用反射
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类 , Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 客户端初始化处理
                        protected void initChannel(SocketChannel client) throws Exception {
                            // 无锁化串行编程
                            //Netty对HTTP协议的封装，顺序有要求
                            // HttpResponseEncoder 编码器
                            // 责任链模式，双向链表Inbound OutBound
                            client.pipeline().addLast(new HttpResponseEncoder());
                            // HttpRequestDecoder 解码器
                            client.pipeline().addLast(new HttpRequestDecoder());
                            // 业务逻辑处理
                            client.pipeline().addLast(new NettyTomcatHandler());
                        }
                    })
                    // 针对主线程的配置 分配线程最大数量 128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //启动服务
            ChannelFuture f=server.bind(port).sync();
            System.out.println("NettyTomcat已经启动，监听的端口"+port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class NettyTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof HttpRequest){
                System.out.println("hello");
                HttpRequest req=(HttpRequest) msg;
                GPRequest request=new GPRequest(ctx,req);
                GPResponse response=new GPResponse(ctx,req);
                String url = request.getUrl();
                if(servletMapping.containsKey(url)){
                    servletMapping.get(url).service(request,response);
                }else {
                    response.write("404 - Not Found");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        }
    }

    public static void main(String[] args) {
        new NetyTomcat().start();
    }


}
