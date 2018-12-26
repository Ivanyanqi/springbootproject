package cn.ivan.client.other;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/30
 */
@Slf4j
public class RealSubject implements Subject{

    public void sayHello(){
        log.info("======= hello world");
    }

}
