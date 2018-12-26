package cn.ivan.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
 *  启动内置的web容器，发起http请求测试，不同于mockMVC的模拟controller测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClientApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate ;


    @Test
    public void contextLoads(){

        String forObject = testRestTemplate.getForObject("/test", String.class);
        log.info("=============" + forObject);


    }



}
