package com.zjy.domain;

import com.zjy.constant.RpcConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 11:12 上午
 * @Description:
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 相应信息
     */
    private String message;
    /**
     * 相应数据
     */
    private T data;

    public static <T> RpcResponse success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatus(RpcConstant.SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse fail(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatus(RpcConstant.FAIL_CODE);
        response.setMessage(RpcConstant.FAIL_MESSAGE);
        return response;
    }
}
