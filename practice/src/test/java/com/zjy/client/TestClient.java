package com.zjy.client;

import com.zjy.api.HelloService;
import com.zjy.domain.HelloObject;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 2:07 下午
 * @Description:
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("localhost", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(1, "rpc try");
        String res = helloService.hello(helloObject);
        System.out.println("res = " + res);
    }
}
