package cn.ivan.dubbo.client;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  dubbo customers  example
 * @author yanqi
 * @version 1.0
 */

@SpringBootApplication
@EnableDubboConfiguration
public class SpringBootDubboClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboClientApplication.class);
    }
}
