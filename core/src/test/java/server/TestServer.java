package server;

import com.zjy.api.HelloService;
import com.zjy.api.RegistryService;
import com.zjy.api.impl.HelloServiceImpl;
import com.zjy.api.impl.RegistryServiceImpl;
import com.zjy.server.RpcServer;

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
        RpcServer server = new RpcServer(registryService);
        server.start(9000);
    }
}
