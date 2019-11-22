package cn.ivan.client.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *  日志切面配置
 * @author yanqi
 * @version 1.0
 */

@Component
@Aspect
public class LogConfig {

    @Pointcut("@annotation(cn.ivan.client.config.Log)")
    public void pointCut(){}

   /* @Before("pointCut()")
    public void log(JoinPoint point){
        //注解作用在方法上,获取的是方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        String description = annotation.description();
        System.out.println("========" + description);
    }*/

    /**
     *  环绕通知适用ProceedingJoinPoint,要值 process方法一执行方法
     *  环绕通知要有返回值，返回值类型，和方法类型一致
     * @param joinPoint
     */
   @Around("pointCut()")
   public Object logAround(ProceedingJoinPoint joinPoint){
       System.out.println("=================执行之前==============");
       try {
           Object proceed = joinPoint.proceed();
           System.out.println("=================执行之后==============");
           return proceed;
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       return null;

   }
}
