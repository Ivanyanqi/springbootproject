package cn.ivan.webflux.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 *
 *  mono 0..1 的 序列
 *  flux 0..n 的 序列
 * @author yanqi69
 * @date 2020/5/29
 */
@Slf4j
@RestController
public class BasicController {


    private String[] arr = {"1","2","3"};

    @GetMapping("/api/say")
    public String say(){
        log.info("===============mvc start =================");
        String result = genernate();
        log.info("===============mvc end =================");
        return result;
    }

    @GetMapping("/api/say/flux")
    public Mono<String> sayFlux(){
        log.info("===============flux start =================");
        // 响应式 servlet 线程立即返回 ,有spring 处罚流的终止操作， 手动触发会阻塞线程
        // 调用方法返回mono流，否则和mvc 模式一样， mono流是一个惰性操作
        Mono<String> just = Mono.fromSupplier(this::genernate);
        log.info("===============flux end =================");
        //return Mono.just(result);
        return just;
    }


    @GetMapping("/api/say/fluxs")
    public Flux<String> sayFluxs(){
        log.info("===============flux start =================");
        // 响应式 servlet 线程立即返回 ,有spring 处罚流的终止操作， 手动触发会阻塞线程
        // 调用方法返回mono流，否则和mvc 模式一样， mono流是一个惰性操作
        Flux<String> just = Flux.fromStream(Stream.of(arr).peek((a)->{
            log.info("=====debug {} ",a);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        log.info("===============flux end =================");
        //return Mono.just(result);
        return just;
    }

    /**
     *  sse 模式 已流的形式返回数据
     * @return
     */
    @GetMapping( value =  "/api/say/stream/fluxs" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sayFluxsStream(){
        log.info("===============flux start =================");
        // 响应式 servlet 线程立即返回 ,有spring 处罚流的终止操作， 手动触发会阻塞线程
        // 调用方法返回mono流，否则和mvc 模式一样， mono流是一个惰性操作
        Flux<String> just = Flux.fromStream(Stream.of(arr).peek((a)->{
            log.info("=====debug {} ",a);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        log.info("===============flux end =================");
        //return Mono.just(result);
        return just;
    }


    private String genernate(){
        // 模拟耗时
        try {
            log.info("========= 耗时操作");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
