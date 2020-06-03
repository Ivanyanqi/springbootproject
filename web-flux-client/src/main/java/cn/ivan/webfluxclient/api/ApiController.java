package cn.ivan.webfluxclient.api;

import cn.ivan.webfluxclient.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author yanqi69
 * @date 2020/6/3
 */
@RestController
public class ApiController {

    @Autowired
    private BasicService basicService;

    @GetMapping("/client/say")
    public Mono<String> string(){
        return basicService.sayFlux();
    }
}
