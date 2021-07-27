package com.zjy.api.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zjy.api.RegistryService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 5:02 下午
 * @Description:
 */
@Slf4j
public class RegistryServiceImpl implements RegistryService {

    private final Map<String, Object> registryMap = Maps.newConcurrentMap();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length < 1) {
            log.error("该方法没有实现任何接口");
            return;
        }
        //如果说一个类实现了多个接口，那么这些接口对应的都是同一个实现类
        Arrays.stream(interfaces).forEach(i -> registryMap.put(i.getCanonicalName(), service));
        log.info("向：接口{} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = registryMap.get(serviceName);
        if (service == null) {
            log.error("服务：{} 没有注册", serviceName);
            return null;
        }
        return service;
    }
}
