package cn.ivan.dubbo.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanqi69
 * @date 2019/8/13
 */
@Configuration
@ConfigurationProperties("spring.dubbo")
@Data
public class DubboConfig {
    private String version;
}
