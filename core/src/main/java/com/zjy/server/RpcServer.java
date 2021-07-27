package com.zjy.server;

import com.zjy.api.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 12:44 下午
 * @Description:
 */
@Slf4j
public class RpcServer {

    private ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    private static final  Integer DEFAULT_CORE_POOL_SIZE = 10;
    private static final  Integer DEFAULT_MAX_POOL_SIZE = 50;
    private static final  Integer DEFAULT_QUEUE_CAPACITY = 100;
    private static final  Integer DEFAULT_KEEP_ALIVE_TIME = 60;
    private static final  String DEFAULT_THREAD_NAME_PREFIX = "rpc~";

    private final RegistryService registryService;

    public RpcServer(int corePoolSize, int maxCorePoolSize, int queueCapacity, int keepAliveTime,
                     String threadNamePrefix, ThreadPoolExecutor.AbortPolicy abortPolicy, RegistryService registryService) {
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxCorePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(abortPolicy);
        this.registryService = registryService;
        executor.initialize();
    }

    public RpcServer(RegistryService registryService) {
        this(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, DEFAULT_QUEUE_CAPACITY, DEFAULT_KEEP_ALIVE_TIME, DEFAULT_THREAD_NAME_PREFIX,
            new ThreadPoolExecutor.AbortPolicy(), registryService);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("服务器正在启动～～～");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("消费者连接成功，ip: port {}:{}", socket.getInetAddress(), socket.getPort());
                executor.execute(new RequestHandler(socket, registryService));
            }
            executor.shutdown();
        } catch (IOException e) {
            log.error("启动出错：", e);
        }
    }
}
