package com.zjy.client.socket;

import com.zjy.client.RpcClient;
import com.zjy.domain.RpcRequest;
import com.zjy.domain.RpcResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 11:20 上午
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {

    private final RpcClient client;

    public <T> T getProxy(Class<T> clazz) {
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("调用{}#{}方法", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        return client.sendRequest(rpcRequest);
    }
}
