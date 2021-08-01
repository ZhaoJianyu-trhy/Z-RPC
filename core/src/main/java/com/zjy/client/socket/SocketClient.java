package com.zjy.client.socket;

import com.zjy.client.RpcClient;
import com.zjy.domain.RpcRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 11:25 上午
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class SocketClient implements RpcClient {

    private final String host;
    private final int port;

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try(Socket socket =  new Socket(host, port)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(rpcRequest);
            outputStream.flush();
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用发生错误", e);
            return null;
        }
    }
}
