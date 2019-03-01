package cn.ivan.dubbo.server;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  spring boot 启动类
 * @author yanqi
 * @version 1.0
 *
 *  EnableDubboConfiguration，表示要开启dubbo功能. (dubbo provider服务可以使用或者不使用web容器)
 */
@SpringBootApplication
@EnableDubboConfiguration
public class SpringBootDubboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboServerApplication.class);
    }

}
