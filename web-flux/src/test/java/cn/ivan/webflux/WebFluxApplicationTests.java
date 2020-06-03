package cn.ivan.webflux;

import cn.ivan.webflux.client.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class WebFluxApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    DemoService demoService;


    @Test
    public void testProxy() throws InterruptedException {
        log.info("代理类类型 : {}",demoService.getClass().getName());
        Mono<String> result = demoService.get();
        log.info("=================send success {}", result.block());
    }
}
