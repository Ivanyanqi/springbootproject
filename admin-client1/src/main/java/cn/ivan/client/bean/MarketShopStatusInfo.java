package cn.ivan.client.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@Data
@Accessors(chain = true)
public class MarketShopStatusInfo {

    private Long shopId;

    private String channelNo;

    private String shopStatus;

    private String shopStatusFlow;

}
