package cn.ivan.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/15
 */
@Slf4j
@Controller
public class JspController {

    @GetMapping("/test")
    public String test(Model model, HttpServletRequest request){
        String s = request.getRequestURL().toString();

        log.info("===========requestUrl ,{}" ,s );

        log.info("===========requesrUri ,{}" ,request.getRequestURI());

        log.info("==========调用============");
        model.addAttribute("test","test112312");
        return "test1";
    }




}
