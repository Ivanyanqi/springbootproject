package cn.ivan.webflux.client;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
public interface ApiProxyCreator {

    Object creator(Class<?> clazz);
}
