package com.chintec.miniprogram.entity.request;

import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/27 13:38
 */
@Data
public class BeanOrder {
    private Long productId;
    /**
     * 下单客户端id：0小程序，1pc，2为app
     */
    private Integer client = 0;
    /**
     * 支付通道id
     */
    private String passagewayId;
    /**
     * 订单id
     */
    private String orderSn;
    /**
     * 小程序支付需要的
     */
    private String openId;
}
