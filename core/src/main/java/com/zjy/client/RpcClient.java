package com.zjy.client;

import com.zjy.domain.RpcRequest;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 7:20 下午
 * @Description:
 */
public interface RpcClient {

    /**
     * 发送rpc请求
     * @param rpcRequest
     * @return
     */
    Object sendRequest(RpcRequest rpcRequest);
}
