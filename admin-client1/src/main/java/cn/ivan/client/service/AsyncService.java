package cn.ivan.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {


    @Async("executorService")
    public void call(){
        log.info("====start 线程名:{}",Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("====end 线程名:{}",Thread.currentThread().getName());
    }


}
