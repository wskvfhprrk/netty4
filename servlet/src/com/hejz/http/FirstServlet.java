package com.hejz.http;

import com.hejz.tomcat.GPRequest;
import com.hejz.tomcat.GPResponse;
import com.hejz.tomcat.GPServlet;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/9 9:47
 */
public class FirstServlet extends GPServlet {
    @Override
    public void doPost(GPRequest request, GPResponse response) throws Exception{
        this.doPost(request,response);
    }

    @Override
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        response.write("This is first Servlet");
    }
}
