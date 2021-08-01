package server;

import com.zjy.api.HelloService;
import com.zjy.registry.RegistryService;
import com.zjy.api.impl.HelloServiceImpl;
import com.zjy.registry.RegistryServiceImpl;
import com.zjy.server.socket.SocketServer;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 2:07 下午
 * @Description:
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RegistryService registryService = new RegistryServiceImpl();
        registryService.register(helloService);
        SocketServer server = new SocketServer(registryService);
        server.start(9000);
    }
}
