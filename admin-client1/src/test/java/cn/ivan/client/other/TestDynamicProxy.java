package cn.ivan.client.other;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/30
 */
@Slf4j
public class TestDynamicProxy {

    public static void main(String[] args) {

        //jdk动态基于接口实现
        //创建被代理对象
        RealSubject realSubject = new RealSubject();

        //即这个方法会返回和被代理对象同一个借口实例，通过invocationHandler做代理方法的调用
        // 创建 代理对象 1.被代理对象的类加载器，2,被代理对象的所有借口 3,invocationHandler 实例
        Subject o = (Subject)Proxy
                .newProxyInstance(realSubject.getClass().getClassLoader(),
                        realSubject.getClass().getInterfaces(),
                        new ProxyHandler(realSubject));
        o.sayHello();

        UserInfoImpl userInfo = new UserInfoImpl();
        UserInfo info = (UserInfo) Proxy.newProxyInstance(userInfo.getClass().getClassLoader(),
                userInfo.getClass().getInterfaces(),new ProxyHandler(userInfo));
        String name = info.getName();
        log.info("======,{}",name);
    }
}
