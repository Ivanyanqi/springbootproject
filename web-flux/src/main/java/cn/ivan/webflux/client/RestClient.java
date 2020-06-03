package cn.ivan.webflux.client;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
public interface RestClient {


    void init(ServiceInfo serviceInfo);


    Object invoke(MethodInfo methodInfo);

}
