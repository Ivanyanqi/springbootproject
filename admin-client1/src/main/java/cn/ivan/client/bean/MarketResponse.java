package cn.ivan.client.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@Data
@Accessors(chain = true)
public class MarketResponse<T> {

    Integer code;

    String message;

    Boolean isSuccess;

    T data;
}
