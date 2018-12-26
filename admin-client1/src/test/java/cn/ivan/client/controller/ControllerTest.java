package cn.ivan.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 *
 *  WebMvcTest 表示这是一个 MVC测试，其参数可以传入多个待测试的 Controller类， 这里要测试的类是 UserController
 * @author : yanqi
 * @version : 1.0
 * 2018/11/21
 */
@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {JspController.class})
public class ControllerTest {

    /**
     * MockMvc是 Spring提供的专门用于测试 SpringMVC类。只有在WebMvcTest下才会初始化该类
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        // 获取一个视图
        String forwardedUrl = mockMvc.
                perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/view/test1.jsp"))
                .andReturn().getResponse().getForwardedUrl();

        log.info("转发的地址是:{}",forwardedUrl);
    }
}
