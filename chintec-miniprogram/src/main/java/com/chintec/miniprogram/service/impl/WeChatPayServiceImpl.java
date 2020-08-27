package com.chintec.miniprogram.service.impl;

import com.alibaba.fastjson.JSON;
import com.chintec.common.enums.CommonCodeEnum;
import com.chintec.common.util.HttpClientUtil;
import com.chintec.common.util.PayUtils;
import com.chintec.common.util.ResultResponse;
import com.chintec.miniprogram.entity.request.BeanOrder;
import com.chintec.miniprogram.entity.vo.CloseOrderVo;
import com.chintec.miniprogram.entity.vo.OrderVo;
import com.chintec.miniprogram.service.IWeChatPayService;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class WeChatPayServiceImpl implements IWeChatPayService {
    /**
     * 微信appId
     */
    @Value("${wechat.appid}")
    private String appId;
    /**
     * 商户id
     */
    @Value("${wechat.mch_id}")
    private String mchId;
    /**
     * 商户自定义key
     */
    @Value("${wechat.key}")
    private String key;
    /**
     * 支付后回调url
     */
    @Value("${wechat.notify_url}")
    private String notifyUrl;
    /**
     * 签名加密类型:MD5
     */
    @Value("${wechat.sign_type}")
    private String signType;
    /**
     * 支付商品提示名：
     */
    @Value("${wechat.body}")
    private String body;

    /**
     * 微信统一下单接口地址： "https://api.mch.weixin.qq.com/pay/unifiedorder"
     */
    @Value("${wechat.pay_url}")
    private String payUrl;


    /**
     * 此方法用于微信支付接口调用和签名 返回给前台一个签名用于发起微信支付
     * 此方法一般用于小程序，app，扫码支付
     * <p>
     * tradeType JSAPI 为小程序支付，需要openId为毕传
     * 当交易类型为其它类型的时候 不在需要openid
     * 如果想扫码支付可以将code_url返回至前端，由前端利用QRcode.js生成二维码，
     * code_url的有效期为两个小时
     *
     * @param beanOrder 订单类
     * @param request   httpServiletRequest
     * @return ResultResponse
     */
    @Override
    public ResultResponse weChatPay(BeanOrder beanOrder, HttpServletRequest request) {
        try {
            /*
             * 交易类型 ：
             * JSAPI(小程序支付)，
             * NATIVE扫码支付，
             * APP(APP支付)
             */
            String tradeType = "";
            switch (beanOrder.getClient()) {
                case 0:
                    //小程序交易
                    tradeType = "JSAPI";
                    break;
                case 1:
                    //扫码支付
                    tradeType = "NATIVE";
                    break;
                case 2:
                    //app支付
                    tradeType = "APP";
                    break;
                default:
                    break;
            }
            Map<String, String> packageParams = getMap(beanOrder, request);
            packageParams.put("trade_type", tradeType);
            //除去数组中的空值和签名参数
            packageParams = PayUtils.paraFilter(packageParams);
            //把数组所有元素,按照"参数=参数值"的模式用"&"字符拼接成字符串
            String prestr = PayUtils.createLinkString(packageParams);
            // MD5运算生成签名,这里是第一次签名,用于调用统一下单接口
            String mysign = PayUtils.sign(prestr, key, "utf-8").toUpperCase();
            String nonceStr = packageParams.get("nonce_str");
            log.info("=======================第一次签名：" + mysign + "============ ======");
            //拼接统一下单接口使用的XML数据,要将上一步生成的签名一起拼接进去
            OrderVo orderVo = new OrderVo(appId, body, URLEncoder.encode(mchId.trim(), StandardCharsets.UTF_8), nonceStr, notifyUrl, beanOrder.getOpenId(), beanOrder.getOrderSn(), packageParams.get("spbill_create_ip"), packageParams.get("money"), tradeType.trim(), mysign);
            String xmlString = getXMLString(orderVo);
            log.info("调试模式_统一下单接口请求XML数据：" + xmlString);
            //调用统一下单接口,并接受返回的结果
            String result = PayUtils.httpRequest(payUrl, "POST", xmlString);
            log.info("调试模式_统一下单接口返回XML数据：" + result);
            //将解析结果存储在HashMap中
            Map map = PayUtils.doXMLParse(result);
            //返回状态码,这个只是通信标识符
            String returnCode = (String) map.get("return_code");
            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if (CommonCodeEnum.COMMON_SUCCESS_CODE.getMessage().equals(returnCode)) {
                //业务结果状态码 交易标识符
                String resultCode = (String) map.get("result_code");
                if (CommonCodeEnum.COMMON_SUCCESS_CODE.getMessage().equals(resultCode)) {
                    String sign = "";
                    //判断客户端方式
                    if (beanOrder.getClient() == 1) {
                        String url = (String) map.get("code_url");
                        response.put("codeUrl", url + "&redirect_url=" + notifyUrl);
                        sign = JSON.toJSONString(response);
                        ResultResponse.successResponse("支付调用成功", sign);
                    } else if (beanOrder.getClient() == 0 || beanOrder.getClient() == 3) {
                        String prepayId = (String) map.get("prepay_id");
                        response.put("nonceStr", nonceStr);
                        response.put("package", "prepay_id=" + prepayId);
                        long timeStamp = System.currentTimeMillis() / 1000;
                        //这边要将返回的时间戳转化成字符串,不然小程序端调用wx.requestPayment方法会报签名错误
                        response.put("timeStamp", timeStamp + "");
                        //再次签名,这个签名用于小程序端调用wx.requesetPayment方法
                        String linkString = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepayId + "&signType=" + signType + "&timeStamp=" + timeStamp;
                        String paySign = PayUtils.sign(linkString, key, "utf-8").toUpperCase();
                        log.info(paySign);
                        response.put("paySign", paySign);
                        response.put("appid", appId);
                        sign = JSON.toJSONString(response);
                        return ResultResponse.successResponse("支付调用成功", sign);
                    }
                }
                return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), (String) map.get("err_code_des"));
            }
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), (String) map.get("return_msg"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), e.getMessage());
        }
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
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
            //通信标识符
            String returnCode = (String) map.get("return_code");
            log.info(returnCode);
            //判断是否成功，成功告诉微信你收到结果
            //失败告诉微信你未收到结果，让它在次调用
            if (CommonCodeEnum.COMMON_SUCCESS_CODE.getMessage().equals(returnCode)) {
                String outTradeNo = (String) map.get("out_trade_no");
                String resultCode = (String) map.get("result_code");
                //支付结果判断，成功支付和失败支付走不同的业务逻辑
                if (CommonCodeEnum.COMMON_SUCCESS_CODE.getMessage().equals(resultCode)) {
                    //通知微信服务器已经支付成功
                    //添加支付成功的业务逻辑
                    //TODO
                    log.info(outTradeNo);
                } else {

                       /*
                通知微信服务器已经支付失败
                添加支付失败的业务逻辑
                支付失败后需要调用关闭微信支付的关闭订单接口
                避免用户重复支付,以及支付超时注意的是订单生成后不能马上调用关单接口，最短调用时间间隔为五分钟
                */
                    //TODO
                    log.info(outTradeNo);
                }
                //设置成功收到支付结果的提示 success
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                //设置通信失败的结果的提示 success
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

    /**
     * 此方法提供了支付失败后关闭微信的订单功能
     *
     * @return ResultResponse
     */
    private ResultResponse closeOrder(Map<String, String> data, String orderNo) {
        try {
            //除去数组中的空值和签名参数
            data = PayUtils.paraFilter(data);
            String sign = PayUtils.sign(PayUtils.createLinkString(data), key, "UTF-8");
            CloseOrderVo closeOrderVo = new CloseOrderVo(data.get("appid"), data.get("mch_id"), PayUtils.getRandomStringByLength(32), orderNo, sign);
            String xmlString = getXMLString(closeOrderVo);
            //调用统一下单接口,并接受返回的结果
            String result = PayUtils.httpRequest("这个微信关闭订单接口地址", "POST", xmlString);
            Map map = PayUtils.doXMLParse(result);
            String returnCode = (String) map.get("return_code");
            if (CommonCodeEnum.COMMON_SUCCESS_CODE.getMessage().equals(returnCode)) {
                if (CommonCodeEnum.COMMON_SUCCESS_CODE.getMessage().equals(map.get("result_code"))) {
                    return ResultResponse.successResponse("订单关闭成功");
                } else {
                    return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), (String) map.get("err_code_des"));
                }
            }
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), (String) map.get("return_msg"));
        } catch (Exception e) {
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), e.getMessage());
        }
    }

    /**
     * 这个方法提供微信退款
     * 此方法需要双向证书认证
     * 一个是商户申请的资金回滚的api证书
     * 一个时sign 签名认证
     *
     * @return ResultResponse
     */
    private ResultResponse refund() {
        return null;
    }

    private Map<String, String> getMap(BeanOrder beanOrder, HttpServletRequest request) {

        //生成的随机字符串
        String nonceStr = PayUtils.getRandomStringByLength(32);
        //获取本机的IP地址
        String spbillCreateIp = HttpClientUtil.getIpAddr(request);
        //支付金额,单位：分,这边需要转成字符串类型,否则后面的签名会失败
        //一般是根据订单号查询支付金额的 订单一般生成在redis里面
        String money = "";
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("body", body);
        packageParams.put("mch_id", mchId);
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("notify_url", notifyUrl);
        packageParams.put("spbill_create_ip", spbillCreateIp);
        //设置订单号
        packageParams.put("out_trade_no", beanOrder.getOrderSn());
        //支付金额,这边需要转成字符串类型,否则后面的签名会失败
        packageParams.put("total_fee", money);
        return packageParams;
    }

    private String getXMLString(Object o) {
        XStream xstream = new XStream();
        xstream.alias("xml", o.getClass());
        String xml = xstream.toXML(o);
        xml = xml.replaceAll("__", "_");
        return xml;
    }
}
