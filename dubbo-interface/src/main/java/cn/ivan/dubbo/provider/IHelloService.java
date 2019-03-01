package cn.ivan.dubbo.provider;

public interface IHelloService {
    String saySomething(String msg);

    User getUser(int userId);
}
