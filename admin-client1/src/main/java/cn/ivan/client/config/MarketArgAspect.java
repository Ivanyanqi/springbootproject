package cn.ivan.client.config;

import cn.ivan.client.bean.MarketRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@Slf4j
@Aspect
@Component
public class MarketArgAspect {

    @Pointcut("@annotation(cn.ivan.client.config.WJArgResolve)")
    public void pointCut(){}


    @Autowired
    private HttpServletRequest request;

    @Before("pointCut()")
    public void before() throws IOException {
        Map<String,Object> marketRequest = Maps.newHashMap();
        marketRequest.put("operator","1234");
        String contentType = request.getContentType();
        log.info("contentType is {}",contentType);
        String method = request.getMethod();
        log.info("请求方法 : {} " ,method);
        boolean isPost = StringUtils.equalsIgnoreCase(method, "post");
        Map<String,Object> param = Maps.newHashMap();
        if(isPost){
            //body中获取数据
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(request.getReader());
            reader.lines().forEach(sb::append);
            log.info("request body is {}" ,sb.toString());
            param = JSON.parseObject(sb.toString(),new TypeReference<Map<String,Object>>(){});
        }else {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String name = parameterNames.nextElement();
                String value = request.getParameter(name);
                param.put(name,value);
            }
        }
        marketRequest.put("in",param);
        request.setAttribute("data",JSON.toJSONString(marketRequest));
    }


}
