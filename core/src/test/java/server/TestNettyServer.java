package server;

import com.zjy.api.HelloService;
import com.zjy.api.impl.HelloServiceImpl;
import com.zjy.registry.RegistryService;
import com.zjy.registry.RegistryServiceImpl;
import com.zjy.server.netty.NettyServer;

public class TestNettyServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RegistryService registry = new RegistryServiceImpl();
        registry.register(helloService);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999);
    }
}
