package cn.ivan.dubbo.server.dubboService.impl;

import cn.ivan.dubbo.provider.IHelloService;
import cn.ivan.dubbo.provider.User;
import cn.ivan.dubbo.server.config.DubboConstant;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 *  发布 dubbo 服务, 接口 和 版本号
 *  其中interfaceClass是要发布服务的接口.
 * @author yanqi
 * @version 1.0
 */
@Service(interfaceClass = IHelloService.class,version = DubboConstant.DUBBO_SERVER_VERSION)
@Component
public class HelloServerImpl implements IHelloService {

    @Override
    public String saySomething(String msg) {
        return "dubbo server : " + msg;
    }

    @Override
    public User getUser(int userId) {
        return User.of(userId).setUsername("yanqi").setAge(18);
    }
}
