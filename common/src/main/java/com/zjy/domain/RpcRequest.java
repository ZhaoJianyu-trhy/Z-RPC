package com.zjy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 11:07 上午
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 调用接口名
     */
    private String interfaceName;
    /**
     * 调用方法名
     */
    private String methodName;
    /**
     * 调用参数
     */
    private Object[] parameters;
    /**
     * 调用参数类型
     */
    private Class<?>[] paramTypes;


}
