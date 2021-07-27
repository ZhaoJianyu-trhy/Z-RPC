package com.zjy.server;

import com.zjy.api.HelloService;
import com.zjy.api.HelloServiceImpl;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 2:07 下午
 * @Description:
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer server = new RpcServer();
        server.register(helloService, 9000);
    }
}
