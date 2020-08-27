package com.chintec.miniprogram.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关闭订单序列化类
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/27 11:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseOrderVo {
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String out_trade_no;
    private String sign;
}
