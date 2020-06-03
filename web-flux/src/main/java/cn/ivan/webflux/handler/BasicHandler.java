package cn.ivan.webflux.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 *
 *  handler  输入为 serverRequest 输出为 Mono<ServerResponse> function 处理具体业务 相当于 mvc 里的 controller
 * @author yanqi69
 * @date 2020/6/1
 */
@Slf4j
@Component
public class BasicHandler {



    public Mono<ServerResponse> say(ServerRequest request){
        return ServerResponse.ok().bodyValue("rector ok");
    }


    public Mono<ServerResponse> sayFlux(ServerRequest request){
        log.info("  router function start ========");
        Flux<Integer> just = Flux.fromStream(Stream.of(1,2,3).peek((a)->{
            log.info("=====debug {} ",a);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        log.info("  router function end ========");
        return ServerResponse.ok().body(just,Integer.class);
    }

    public Mono<ServerResponse> sayFluxStream(ServerRequest request){
        log.info("  router function stream start ========");
        Flux<Integer> just = Flux.fromStream(Stream.of(1,2,3).peek((a)->{
            log.info("=====debug {} ",a);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        log.info("  router function stream end ========");
        // 返回 stream sse 请求
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(just,Integer.class);
    }

}
