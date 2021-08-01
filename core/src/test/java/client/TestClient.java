package client;

import com.zjy.client.netty.NettyClient;
import com.zjy.client.socket.RpcClientProxy;
import com.zjy.api.HelloService;
import com.zjy.domain.HelloObject;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 2:07 下午
 * @Description:
 */
public class TestClient {
    public static void main(String[] args) {
        NettyClient client = new NettyClient("localhost", 8080);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(1, "rpc try");
        String res = helloService.hello(helloObject);
        System.out.println("res = " + res);
    }
}
