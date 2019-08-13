package cn.ivan.client.constants;

import cn.ivan.client.config.ThreadPoolConfig;
import cn.ivan.client.util.SpringContextUtil;

/**
 * @author yanqi69
 * @date 2019/8/13
 */
public interface JavaConstant {
    int VERSION = SpringContextUtil.getBean(ThreadPoolConfig.class).getTimeout();
}
