package cn.ivan.dubbo.client.clientservice;

import cn.ivan.dubbo.provider.IHelloService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 *  客户服务层
 * @author yanqi
 * @version 1.0
 */

@Service
public class ClientService {
    /**
     * 注入dubbo 远程服务
     * 服务提供者和服务消费者的接口权限定名必须一样
     */
    @Reference(interfaceClass = IHelloService.class,version = "1.0.0")
    private IHelloService iHelloService;


    public String testDubboRPC(String msg){
        return iHelloService.saySomething(msg);
    }
}
