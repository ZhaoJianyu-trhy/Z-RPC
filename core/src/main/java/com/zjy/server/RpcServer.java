package com.zjy.server;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 7:20 下午
 * @Description:
 */
public interface RpcServer {

    /**
     * 在指定端口启动服务器
     * @param port 端口
     */
    void start(int port);
}
