package cn.ivan.webflux.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *  代理类有serviceInfo  和 methodInfo
 *
 * 定义服务信息
 * @author yanqi69
 * @date 2020/6/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo {


    private String serviceUrl;

}
