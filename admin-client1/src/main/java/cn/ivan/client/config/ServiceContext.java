package cn.ivan.client.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yanqi69
 * @date 2019/12/17
 */
@Accessors(chain = true)
@Data
public class ServiceContext {

    private String request;

    private String response;

}
