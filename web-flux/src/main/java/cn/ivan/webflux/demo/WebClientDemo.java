package cn.ivan.webflux.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
@Slf4j
public class WebClientDemo {


    public static void main(String[] args) throws InterruptedException {
        Mono<String> stringMono = WebClient.create().get().uri("http://www.baidu.com").retrieve().bodyToMono(String.class);
        log.info("success");
        // 阻塞获取响应
       /* String block = stringMono.block();
        log.info(block);*/

        // rector线程订阅消费
        stringMono.subscribe(s->log.info("结果：{}", s));
        // 阻塞当前线程等待守护线程执行完毕
        Thread.currentThread().join();
    }
}
