package cn.ivan.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

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
