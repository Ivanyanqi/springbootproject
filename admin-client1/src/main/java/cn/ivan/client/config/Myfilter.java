package cn.ivan.client.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *  FILTER 也是由spring 管理的
 */
@WebFilter(filterName = "myFilter" ,urlPatterns = "/*")
@Slf4j
public class Myfilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("============进入过滤器=====================");
        String name = servletRequest.getParameter("name");
        try {
            if (name.equals("123")){
                throw new Exception("===========抛异常了=======");
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e){


//            servletResponse.setContentType("application/json;charset=utf-8");
//            Map<String,String> result = new HashMap<>();
//            result.put("name","张三");
//            servletResponse.getWriter().print(JSON.toJSONString(result));

            // 只要有往上抛异常，才会有spring 框架默认异常处理
            log.info("===========进入filter的异常===========");
            throw new RuntimeException("=================lllll===========");
        }



    }

    @Override
    public void destroy() {

    }
}
