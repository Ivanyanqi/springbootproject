package cn.ivan.client.service.impl;

import cn.ivan.client.bean.MarketRequest;
import cn.ivan.client.bean.MarketResponse;
import cn.ivan.client.bean.MarketShopStatusInfo;
import cn.ivan.client.bean.MarketShopUserPinParam;
import cn.ivan.client.config.Log;
import cn.ivan.client.service.IChainService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yanqi69
 * @date 2019/12/12
 */
@Service(value = "chainService")
@Slf4j
public class ChainServiceImpl implements IChainService{


    @Log(description = "调用接口")
    @Override
    public MarketResponse<MarketShopStatusInfo> getMarketShopStatus(MarketRequest<MarketShopUserPinParam> request) {
        log.info("pram ===  {}" , JSON.toJSONString(request));
        return new MarketResponse<MarketShopStatusInfo>()
                .setCode(200)
                .setIsSuccess(true)
                .setData(new MarketShopStatusInfo()
                        .setShopId(1L)
                        .setChannelNo("1111")
                        .setShopStatus("1")
                        .setShopStatusFlow("1"));
    }
}
