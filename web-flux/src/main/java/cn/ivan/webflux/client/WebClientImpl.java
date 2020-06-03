package cn.ivan.webflux.client;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
public class WebClientImpl implements RestClient {

    private WebClient webClient;

    @Override
    public void init(ServiceInfo serviceInfo) {
        this.webClient = WebClient.create(serviceInfo.getServiceUrl());
    }

    @Override
    public Object invoke(MethodInfo methodInfo) {
       return webClient.method(methodInfo.getMethod())
                .uri(methodInfo.getUri())
                .retrieve()
                .bodyToMono(methodInfo.getRetrunActualType());
    }
}
