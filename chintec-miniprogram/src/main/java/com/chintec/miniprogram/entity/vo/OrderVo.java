package com.chintec.miniprogram.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一个支付序列化类
 * @author tangxinli
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    private String appid;

    private String body;

    private String mch_id;

    private String nonce_str;

    private String notify_url;

    private String openid;

    private String out_trade_no;

    private String spbill_create_ip;

    private String total_fee;

    private String trade_type;

    private String sign;
}
