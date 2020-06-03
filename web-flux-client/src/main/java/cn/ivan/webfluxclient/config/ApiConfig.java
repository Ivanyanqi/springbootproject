package cn.ivan.webfluxclient.config;

import cn.ivan.mountain.facorty.ApiProxyFactory;
import cn.ivan.mountain.proxy.ApiProxyCreator;
import cn.ivan.mountain.proxy.impl.JdkApiProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanqi69
 * @date 2020/6/3
 */
@Configuration
public class ApiConfig {

    @Bean
    public ApiProxyCreator apiProxyCreator(){
        return new JdkApiProxyCreator();
    }

    @Bean
    public ApiProxyFactory apiProxyFactory(){
        return new ApiProxyFactory();
    }

}
