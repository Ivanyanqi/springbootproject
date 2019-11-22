package cn.ivan.client.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * @author yanqi69
 * @date 2019/11/22
 */
@Component
@Slf4j
public class ArgsParseInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contentType = request.getContentType();
        log.info("contentType is {}",contentType);
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(request.getReader());
        reader.lines().forEach(sb::append);
        log.info("request body is {}" ,sb.toString());
        request.setAttribute("data",sb.toString());
        return true;
    }
}
