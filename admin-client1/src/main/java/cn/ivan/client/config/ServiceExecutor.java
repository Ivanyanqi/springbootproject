package cn.ivan.client.config;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yanqi69
 * @date 2019/12/17
 */
@Slf4j
@Data
@Accessors(chain = true)
public class ServiceExecutor {

    private Object service;

    private String method;

    public String executor(String request) {

        Class<?> aClass = service.getClass();
        Method[] methods = aClass.getMethods();
        Method exeMethod = null;
        for (Method m : methods) {
            if(m.getName().equals(method)){
                exeMethod = m;
            }
        }
        if(exeMethod == null){
            throw new RuntimeException("method not found");
        }
        Class<?>[] parameterTypes = exeMethod.getParameterTypes();
        Object data = JSON.parseObject(request, parameterTypes[0]);
        try {
             return JSON.toJSONString(exeMethod.invoke(service, data));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

}
