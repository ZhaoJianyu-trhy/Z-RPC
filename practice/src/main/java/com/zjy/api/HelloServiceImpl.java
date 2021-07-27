package com.zjy.api;

import com.zjy.api.HelloService;
import com.zjy.domain.HelloObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 10:50 上午
 * @Description:
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(HelloObject helloObject) {
        log.info("接收到：{}", helloObject.getMessage());
        return "返回调用，id = " + helloObject.getId();
    }
}
