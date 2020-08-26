package com.chintec.miniprogram.service.impl;

import com.alibaba.fastjson.JSON;
import com.chintec.common.enums.CommonCodeEnum;
import com.chintec.common.util.HttpClientUtil;
import com.chintec.common.util.PayUtils;
import com.chintec.common.util.ResultResponse;
import com.chintec.miniprogram.entity.vo.OrderVo;
import com.chintec.miniprogram.service.IWeCatPayService;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 此为实现类 实现支付接口
 * 提供了微信支付方法 weCatPay
 * 以及回调的方法
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/26 15:13
 */
@Service
@Slf4j
public class WeCatPayServiceImpl implements IWeCatPayService {
    /**
     * 微信appId
     */
    @Value("${}")
    private String appId;
    /**
     * 商户id
     */
    @Value("${}")
    private String mchId;
    /**
     * 商户自定义key
     */
    @Value("${}")
    private String key;
    /**
     * 支付后回调url
     */
    @Value("${}")
    private String notifyUrl;
    /**
     * 签到类型:MD5
     */
    @Value("${}")
    private String signType;
    /**
     * 交易类型 ：JSAPI
     */
    @Value("${}")
    private String tradeType;
    /**
     * 微信统一下单接口地址： "https://api.mch.weixin.qq.com/pay/unifiedorder"
     */
    @Value("${}")
    private String payUrl;


    /**
     * 此方法用于微信支付接口调用和签名 返回给前台一个签名用于发起微信支付
     * 此方法一般用于app和小程序调用微信支付
     *
     * @param openId  用户openId唯一
     * @param orderSn 订单号 唯一
     * @param request httpServiletRequest
     * @return ResultResponse
     */
    @Override
    public ResultResponse weCatPay(String openId, String orderSn, HttpServletRequest request) {
        String sign = "";
        try {
            //生成的随机字符串
            String nonceStr = PayUtils.getRandomStringByLength(32);
            //商品名称
            String body = "buylevel";
            //获取本机的IP地址
            String spbillCreateIp = HttpClientUtil.getIpAddr(request);
            //支付金额,单位：分,这边需要转成字符串类型,否则后面的签名会失败
            String money = "";
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", appId);
            packageParams.put("body", body);
            packageParams.put("mch_id", mchId);
            packageParams.put("nonce_str", nonceStr);
            packageParams.put("notify_url", notifyUrl);
            packageParams.put("openid", openId);
            packageParams.put("spbill_create_ip", spbillCreateIp);
            //设置订单号
            packageParams.put("out_trade_no", orderSn);
            //支付金额,这边需要转成字符串类型,否则后面的签名会失败
            packageParams.put("total_fee", money);
            packageParams.put("trade_type", tradeType);
            //除去数组中的空值和签名参数
            packageParams = PayUtils.paraFilter(packageParams);
            //把数组所有元素,按照"参数=参数值"的模式用"&"字符拼接成字符串
            String prestr = PayUtils.createLinkString(packageParams);
            // MD5运算生成签名,这里是第一次签名,用于调用统一下单接口
            String mysign = PayUtils.sign(prestr, key, "utf-8").toUpperCase();
            log.info("=======================第一次签名：" + mysign + "============ ======");
            //拼接统一下单接口使用的XML数据,要将上一步生成的签名一起拼接进去
            OrderVo orderVo = new OrderVo(body, URLEncoder.encode(mchId.trim(), StandardCharsets.UTF_8), nonceStr, notifyUrl, openId, orderSn, spbillCreateIp, money, tradeType.trim(), mysign);
            orderVo.setAppid(appId);
            XStream xstream = new XStream();
            xstream.alias("xml", OrderVo.class);
            String xml = xstream.toXML(orderVo);
            xml = xml.replaceAll("__", "_");
            log.info(xml);
            log.info("调试模式_统一下单接口请求XML数据：" + xml);
            //调用统一下单接口,并接受返回的结果
            String result = PayUtils.httpRequest(payUrl, "POST", xml);
            log.info("调试模式_统一下单接口返回XML数据：" + result);
            //将解析结果存储在HashMap中
            Map map = PayUtils.doXMLParse(result);
            //返回状态码
            String returnCode = (String) map.get("return_code");

            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if ("SUCCESS".equals(returnCode)) {
                String prepayId = (String) map.get("prepay_id");
                response.put("nonceStr", nonceStr);
                response.put("package", "prepay_id=" + prepayId);
                long timeStamp = System.currentTimeMillis() / 1000;
                //这边要将返回的时间戳转化成字符串,不然小程序端调用wx.requestPayment方法会报签名错误
                response.put("timeStamp", timeStamp + "");

//                String stringSignTemp = "appid=" + WxPayConfig.appid + "&timestamp=" + timeStamp + "&noncestr=" + nonce_str + "&package=prepay_id="
//                        + prepayId + "&signtype=" + WxPayConfig.SIGNTYPE;

                //再次签名,这个签名用于小程序端调用wx.requesetPayment方法
                String linkString = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepayId + "&signType=" + signType + "&timeStamp=" + timeStamp;
                String paySign = PayUtils.sign(linkString, key, "utf-8").toUpperCase();
                log.info(paySign);
                response.put("paySign", paySign);
                response.put("appid", appId);
                sign = JSON.toJSONString(response);
            } else {
                return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), "支付失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), e.getMessage());
        }
        return ResultResponse.successResponse("支付调用成功", sign);
    }

    /**
     * 此方法用于微信支付后的回调方法
     * 一般是成功或是失败
     * 失败就会就改变订单状态为支付失败状态，让用户再次支付
     * 成功改变订单状态为已支付
     * 改变订单状态使用mq 确认模式去改变订单状态
     * 采用确认和补偿机制
     *
     * @param request  re
     * @param response re
     */
    @Override
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //sb为微信返回的xml
            String notityXml = sb.toString();
            String resXml = "";
            log.info("接收到的报文：" + notityXml);

            Map map = PayUtils.doXMLParse(notityXml);

            String returnCode = (String) map.get("return_code");
            log.info(returnCode);
            if ("SUCCESS".equals(returnCode)) {
                //验证签名是否正确
                String outTradeNo = (String) map.get("out_trade_no");
                log.info(outTradeNo);
                //通知微信服务器已经支付成功
                //添加支付成功的业务逻辑
                //TODO
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                //通知微信服务器已经支付失败
                //添加支付失败的业务逻辑
                //TODO
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            log.info("微信支付回调数据结束");
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
