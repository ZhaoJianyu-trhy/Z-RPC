package com.zjy.server;

import com.zjy.domain.WorkThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private ThreadPoolTaskExecutor executor;

    public RpcServer() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("rpc~");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
    }

    public void register(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("服务器正在启动～～～");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("客户端连接成功，ip：{}", socket.getInetAddress());
                executor.execute(new WorkThread(socket, service));
            }
        } catch (IOException e) {
            log.error("连接出错：", e);
        }
    }
}
