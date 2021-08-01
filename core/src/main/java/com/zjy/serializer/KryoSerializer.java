package com.zjy.serializer;

import com.zjy.constant.SerializerCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KryoSerializer implements CommonSerializer {



    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        return null;
    }

    @Override
    public int getCode() {
        return SerializerCode.Kryo.getCode();
    }
}
