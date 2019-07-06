package cn.ivan.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// 不加这个，扫描不到 webServlet WebFilter ，WebListener 注解的类
@ServletComponentScan(basePackages = "cn.ivan.client")
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
    }
}
