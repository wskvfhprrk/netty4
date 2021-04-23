package com.hejz.servlet.tomcat;

/**
 * @author hejz
 * @version 1.0
 * @date 2021/4/9 9:27
 */
public abstract class GPServlet {
    public void service(GPRequest reqiest, GPResponse response) throws Exception{
        //由service 决定调用doGet还是doPost
        if("GET".equalsIgnoreCase(reqiest.getMethod())){
            doGet(reqiest,response);
        }else {
            doPost(reqiest,response);
        }
    }

    public abstract void doPost(GPRequest reqiest, GPResponse response) throws Exception;

    public abstract void doGet(GPRequest reqiest, GPResponse response) throws Exception;
}
