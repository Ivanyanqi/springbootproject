package cn.ivan.webfluxclient.service;

import cn.ivan.mountain.annotation.MountainClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * @author yanqi69
 * @date 2020/6/3
 */
@MountainClient("http://localhost:8080")
public interface BasicService {

    @GetMapping("/api/say/flux")
    Mono<String> sayFlux();
}
