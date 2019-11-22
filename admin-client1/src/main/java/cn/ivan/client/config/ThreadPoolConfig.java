package cn.ivan.client.config;

import cn.ivan.client.util.command.LocalCommandExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 线程配置类
 *
 * @author yanqi
 * @version 1.0
 */
@Configuration
@Slf4j
@Data
public class ThreadPoolConfig {

    @Value("${local.command.timeout:-1}")
    private int timeout;

    @Value("${stu.name:}")
    private String name ;

    @Bean
    public ExecutorService executorService() {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("我的线程池-%d").build();
        return new ThreadPoolExecutor(20, 20, 0, TimeUnit.MICROSECONDS, new SynchronousQueue<>(), factory);
    }

    @Bean
    @Log(description = "123")
    public LocalCommandExecutor localCommandExecutorBuilder(ExecutorService executorService) {
        log.info("==========创建执行器===================");
        System.out.println("name ==========" + name.equals("null"));
        return new LocalCommandExecutor
                .LocalCommandExecutorBuilder(executorService)
                .setTimeout(timeout).getInstance();
    }

}
