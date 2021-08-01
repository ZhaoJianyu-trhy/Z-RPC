package client;

import com.zjy.api.HelloService;
import com.zjy.client.netty.NettyClient;
import com.zjy.client.socket.RpcClientProxy;
import com.zjy.domain.HelloObject;

public class TestNettyClient {
    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient("localhost", 9999);
        RpcClientProxy proxy = new RpcClientProxy(nettyClient);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(8, "this is a netty msg");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
