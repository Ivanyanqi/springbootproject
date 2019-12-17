package cn.ivan.client.bean;

import lombok.Data;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@Data
public class MarketRequest<T> {


    String operator;

    T in;
}
