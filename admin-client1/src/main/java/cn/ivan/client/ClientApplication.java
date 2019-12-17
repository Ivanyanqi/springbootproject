package cn.ivan.client;

import cn.ivan.client.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import cn.ivan.client.constants.JavaConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
// 不加这个，扫描不到 webServlet WebFilter ，WebListener 注解的类
//@ServletComponentScan(basePackages = "cn.ivan.client")
@RestController
@EnableAsync
public class ClientApplication {


    @Value("${api.yun.auth}")
    private String authKey;

    public static void main(String[] args) {
        /**
         *  在project下的模块，直接main方法启动，
         *  其工作目录为project的工作目录，所有导致jsp页面找不到
         *  在idea 中设置启动类的工作为module的工作目录
         */
        System.out.println(System.getProperty("user.dir"));
        SpringApplication.run(ClientApplication.class, args);
        System.out.println(JavaConstant.VERSION);
    }

    @Autowired
    private AsyncService asyncService;
    @GetMapping("/testAsync")
    public String testAsync(){
        asyncService.call();
        System.out.println("====================controller=======");
        return "ok";
    }
}
