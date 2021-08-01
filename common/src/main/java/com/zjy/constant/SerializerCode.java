package com.zjy.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializerCode {
    JSON(1),
    Kryo(0);
    private final int code;
}
