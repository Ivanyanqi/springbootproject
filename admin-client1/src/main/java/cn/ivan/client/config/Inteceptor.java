package cn.ivan.client.config;

/**
 * @author yanqi69
 * @date 2019/12/17
 */
public interface Inteceptor {

    Object before(ServiceContext context);


    Object after(ServiceContext context);
}
