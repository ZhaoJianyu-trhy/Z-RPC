package com.zjy.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjy.constant.SerializerCode;
import com.zjy.domain.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonSerializer implements CommonSerializer {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化发生错误", e.getMessage());
            return null;
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = mapper.readValue(bytes, clazz);
            if (obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            log.error("反序列化发生错误", e.getMessage());
            return null;
        }
    }

    private Object handleRequest(Object obj) throws IOException {
        RpcRequest request = (RpcRequest) obj;
        for (int i = 0; i < request.getParamTypes().length; i++) {
            Class<?> paramType = request.getParamTypes()[i];
            if (!paramType.isAssignableFrom(request.getParameters()[i].getClass())) {
                byte[] bytes = mapper.writeValueAsBytes(request.getParameters()[i]);
                request.getParameters()[i] = mapper.readValue(bytes, paramType);
            }
        }
        return request;
    }

    @Override
    public int getCode() {
        return SerializerCode.JSON.getCode();
    }
}
