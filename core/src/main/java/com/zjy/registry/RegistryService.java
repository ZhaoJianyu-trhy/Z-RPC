package com.zjy.registry;

/**
 * @author: ZhaoJianyu03
 * @date: 2021/7/27 4:51 下午
 * @Description:
 */
public interface RegistryService {

    /**
     * 注册一个服务
     * @param service 将一个服务的实现类注册进来
     * @param <T> 服务实现类
     */
    <T> void register(T service);

    /**
     * 根据名称获得服务实现累
     * @param serviceName 服务名
     * @return 服务实现类
     */
    Object getService(String serviceName);
}
