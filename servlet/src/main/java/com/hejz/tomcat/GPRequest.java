package com.hejz.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/9 9:28
 */
public class GPRequest {
    private String method;
    private String url;
    private ChannelHandlerContext ctx;
    private HttpRequest req;

    public GPRequest(InputStream in) {
        try {
            String content="";
            byte[] bytes = new byte[1024];
            int len=0;
                if((len=in.read(bytes))>0){
                    content=new String(bytes,0,len);
                }
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");
            this.method=arr[0];
            this.url=arr[1].split("\\?")[0];
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GPRequest(ChannelHandlerContext ctx, HttpRequest msg) {
        this.ctx=ctx;
        this.req=msg;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> parameters = getParameters();
        List<String> param = parameters.get(name);
        if(null==param){
            return null;
        }else {
            return param.get(0);
        }
    }
}
