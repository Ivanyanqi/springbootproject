package cn.ivan.webflux.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
@Slf4j
@SuppressWarnings("all")
public class CglibApiProxyCreator implements ApiProxyCreator{

    RestClient restClient = new WebClientImpl();
    @Override
    public Object creator(Class<?> clazz) {

        ApiService declaredAnnotation = clazz.getAnnotation(ApiService.class);
        restClient.init(ServiceInfo.builder().serviceUrl(declaredAnnotation.value()).build());

        Enhancer enhancer = new Enhancer();
        // 接口
        enhancer.setInterfaces(new Class[]{clazz});

        enhancer.setCallback(new MethodInterceptor() {
            //MethodProxy：生成的代理类对方法的代理引用
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                MethodInfo methodInfo = new MethodInfo();
                methodInfo.setMethod(HttpMethod.GET);
                methodInfo.setReturnType(method.getReturnType());
                methodInfo.setRetrunActualType(getReturnActualType(method.getGenericReturnType()));
                return restClient.invoke(methodInfo);
            }
        });
        return enhancer.create();
    }

    private Class<?> getReturnActualType(Type type){
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }
}
