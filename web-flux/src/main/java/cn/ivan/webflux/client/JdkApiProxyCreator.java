package cn.ivan.webflux.client;


import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
@SuppressWarnings("all")
public class JdkApiProxyCreator implements ApiProxyCreator {

    RestClient restClient = new WebClientImpl();

    @Override
    public Object creator(Class<?> clazz) {
        ApiService declaredAnnotation = clazz.getAnnotation(ApiService.class);
        restClient.init(ServiceInfo.builder().serviceUrl(declaredAnnotation.value()).build());
        //本省就是一个接口
        return Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz}, this::invoke);
    }


    private Object invoke(Object proxy, Method method, Object... args) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setName(method.getName());
        // 获取方法注解
        Arrays.stream(method.getAnnotations()).forEach(annotation -> {
            if (annotation.annotationType() == RequestMapping.class) {
                RequestMethod[] requestMethods = ((RequestMapping) annotation).method();
                if (requestMethods != null && requestMethods.length > 0) {
                    methodInfo.setMethod(HttpMethod.resolve(requestMethods[0].name()));
                } else {
                    methodInfo.setMethod(HttpMethod.GET);
                }
            } else if (annotation instanceof GetMapping) {
                methodInfo.setMethod(HttpMethod.GET);
            } else if (annotation instanceof PostMapping) {
                methodInfo.setMethod(HttpMethod.POST);
            } else if (annotation instanceof PutMapping) {
                methodInfo.setMethod(HttpMethod.PUT);
            }else  if (annotation instanceof DeleteMapping){
                methodInfo.setMethod(HttpMethod.DELETE);
            }
        });
        methodInfo.setReturnType(method.getReturnType());
        methodInfo.setRetrunActualType(getReturnActualType(method.getGenericReturnType()));
        return restClient.invoke(methodInfo);
    }

    private Class<?> getReturnActualType(Type type){
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }


}
