package cn.ivan.client.other;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/30
 */
@Slf4j
public class ProxyHandler implements InvocationHandler {

    private Object obj;

    public ProxyHandler(Object obj){
        this.obj = obj;

    }
    /**
     *
     * @param proxy  为生成的代理对象，如果此时调用了
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("======代理之前执行=======");
        //如果此是传入proxy，这个方法会陷入死循环，所以这个handler 要持有一个真是类的实例
        Object result = method.invoke(obj, args);
        log.info("======代理之后执行========");
        return result;
    }
}
