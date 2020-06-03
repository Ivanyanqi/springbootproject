package cn.ivan.webflux.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 *   BeanFactory是个Factory，也就是IOC容器或对象工厂，FactoryBean是个Bean
 *
 *   在Spring中，所有的bean都是由BeanFactory(也就是IOC容器)来进行管理的。如XMLBeanFactory就是一种典型的BeanFactory。
 *   常用的ApplicationContext接口也是由BeanFactory接口派生而来，ApplicationContext包含BeanFactory的所有功能，通常建议比BeanFactory优先
 *
 *   对FactoryBean而言，这个bean不是简单的bean，而是一个能生产或者装饰对象生成的工厂bean，
 *   它为IOC容器中bean的实现提供了更加灵活的方式，可以在getObject()方法中灵活配置
 *
 *   例如实现一个FactoryBean，功能：用来代理一个对象，对该对象的所有方法做一个拦截，
 *   在调用前后都输出一行LOG，模仿ProxyFactoryBean的功能
 *
 *
 * @author yanqi69
 * @date 2020/6/2
 */
@Configuration
public class Config {

    @Bean
    public ApiProxyCreator apiProxyCreator(){
        return new CglibApiProxyCreator();
    }


    @Bean
    public FactoryBean<DemoService> demoService(ApiProxyCreator apiProxyCreator){
        return new FactoryBean<DemoService>() {
            // 生成代理类对象
            @SuppressWarnings("all")
            @Override
            public DemoService getObject() throws Exception {
                return (DemoService) apiProxyCreator.creator(DemoService.class);
            }

            // 返回DemoService类型
            @Override
            public Class<?> getObjectType() {
                return DemoService.class;
            }
        };
    }


}
