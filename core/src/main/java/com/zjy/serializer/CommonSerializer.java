package com.zjy.serializer;

public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getBycode(int code) {
        switch (code) {
            case 0:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
