package com.zjy.server;

import com.zjy.api.RegistryService;
import com.zjy.domain.RpcRequest;
import com.zjy.domain.RpcResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 6:12 下午
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class RequestHandler implements Runnable {

    private Socket socket;
    private RegistryService registryService;

    @Override
    public void run() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            RpcRequest request = (RpcRequest)inputStream.readObject();
            String interfaceName = request.getInterfaceName();
            Object service = registryService.getService(interfaceName);
            RpcResponse response = handle(request, service);
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private RpcResponse handle(RpcRequest request, Object service) {
        String methodName = request.getMethodName();
        Class<?>[] paramTypes = request.getParamTypes();
        Object[] parameters = request.getParameters();
        RpcResponse response = null;
        try {
            Method method = service.getClass().getMethod(methodName, paramTypes);
            Object result = method.invoke(service, parameters);
            response = RpcResponse.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("反射调用方法失败, 服务：{}, 方法：{}, 参数: {}",
                service.getClass().getCanonicalName(), methodName, parameters);
            response = RpcResponse.fail();
        } finally {
            return response;
        }
    }
}
