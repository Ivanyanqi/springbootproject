package cn.ivan.client.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;

/**
 * @author yanqi69
 * @date 2019/12/17
 */
@Slf4j
@Component
public class ServiceFactory{

    @Autowired
    private ApplicationContext applicationContext;

    private Properties properties;

    private Map<String,ExecutorChain> mapper = new HashMap<>();

    @PostConstruct
    public void init(){
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("proxy.properties");
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
        initMapper();
        Process.setServiceFactory(this);
    }

    private void initMapper(){
        properties.forEach((k,v) -> {
            String key = (String)k;
            String value = (String)v;
            String[] split = value.split("#");
            String serviceName = split[0];
            String methodName = split[1];
            Object bean = applicationContext.getBean(serviceName);
            mapper.put(key,new ExecutorChain().setExecutor(new ServiceExecutor().setService(bean).setMethod(methodName)));
        });
    }


    public ExecutorChain get(String key){
        return this.mapper.get(key);
    }

}
