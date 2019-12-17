package cn.ivan.client.config;

import cn.ivan.client.interceptors.ArgsParseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * 在springboot2.0之前 我们都是 继承 WebMvcConfigurerAdapter 来实现url的定向，
 * 在springboot 2.0以后 WebMvcConfigurerAdapter 这个方法已经过时
 * 解决方案 :
 *  1.改成继承WebMvcConfigurationSupport这个类，在扩展的类中重写父类的方法即可，
 *  但是这种方式是有问题的，这种方式会屏蔽Spring Boot的@EnableAutoConfiguration中的设置。(自动配置类中WebMvcAutoConfiguration 有初始化条件)
 *  这时候启动项目时会发现映射根本没有成功，读取不到静态的资源也就是说application.properties中添加配置的映射配置没有启动作用，
 *  然后我们会想到重写来进行映射
 *
 *  2.实现WebMvcConfigurer这个接口 ，里面都是default 方法，可以选择性实现
 *
 * @author : yanqi
 * @version : 1.0
 * 2018/11/21
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     *  视图的位置是有配置文件制定的
     *  spring.mvc.view.prefix=/WEB-INF/
     *  spring.mvc.view.suffix=.jsp
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test1").setViewName("test1");
    }

    @Autowired
    private ArgsParseInterceptor argsParseInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(argsParseInterceptor).addPathPatterns("/**");
    }
}
