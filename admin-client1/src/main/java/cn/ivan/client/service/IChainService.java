package cn.ivan.client.service;

import cn.ivan.client.bean.MarketRequest;
import cn.ivan.client.bean.MarketResponse;
import cn.ivan.client.bean.MarketShopStatusInfo;
import cn.ivan.client.bean.MarketShopUserPinParam;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
public interface IChainService {
    MarketResponse<MarketShopStatusInfo> getMarketShopStatus(MarketRequest<MarketShopUserPinParam> request);
}
