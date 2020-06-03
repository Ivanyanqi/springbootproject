package cn.ivan.webflux.client;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * @author yanqi69
 * @date 2020/6/2
 */
@ApiService("https://www.baidu.com")
public interface DemoService {

    @GetMapping("")
    Mono<String> get();
}
