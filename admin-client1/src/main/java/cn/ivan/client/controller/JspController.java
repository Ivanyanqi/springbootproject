package cn.ivan.client.controller;

import cn.ivan.client.config.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/15
 */
@Slf4j
@Controller
public class JspController {


    @GetMapping("/testf")
    @ResponseBody
    public String testFutter(String name){
        if(name.equals("1234")){
            throw new RuntimeException("=================name 错误=========");
        }
        return "123";
    }

    @GetMapping("/test")
    @Log(description = "123")
    public String test(Model model, HttpServletRequest request){
        String s = request.getRequestURL().toString();

        log.info("===========requestUrl ,{}" ,s );

        log.info("===========requesrUri ,{}" ,request.getRequestURI());

        log.info("==========调用============");
        model.addAttribute("test","test112312");
        return "test1";
    }

    @GetMapping("/testReadFileInJar")
    @ResponseBody
    public String testReadFileInJar() throws IOException {


//        URL resource = JspController.class.getClassLoader().getResource("1.txt");
        //System.out.println(resource.getPath());
        //读jar里的配置文件要用getResourceAsStream
        InputStream resourceAsStream = JspController.class.getClassLoader().getResourceAsStream("1.txt");
        String s = FileCopyUtils.copyToString(new InputStreamReader(resourceAsStream));
        System.out.println(s);
        return s;
    }

    @GetMapping("/testForward")
    public void testForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("request");
       // request.getRequestDispatcher("/testReadFileInJar").forward(request,response);
        response.sendRedirect("/client/test");
    }
}
