package com.hejz.servlet.http;

import com.hejz.servlet.tomcat.GPServlet;
import com.hejz.tomcat.GPRequest;
import com.hejz.tomcat.GPResponse;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/9 9:47
 */
public class SecondServlet extends GPServlet {
    @Override
    public void doPost(GPRequest reqiest, GPResponse response) throws Exception{
        this.doPost(reqiest,response);
    }

    @Override
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        response.write("This is second Servlet");
    }
}
