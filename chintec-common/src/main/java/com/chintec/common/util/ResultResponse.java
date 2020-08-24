package com.chintec.common.util;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 返回类，根据业务需求进行状态返回 成功或失败
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/24 9:46
 */
@Data
public class ResultResponse<T> {
    private Integer code = 200;
    private String message = "SUCCESS";
    private boolean isSuccess = true;
    private T data;

    /**
     * 默认成功
     *
     * @return resultResponse
     */
    public static ResultResponse successResponse() {
        return getSuccessResultResponse(null, null);
    }

    /**
     * 自定义返回信息
     *
     * @param message 消息
     * @return resultResponse
     */
    public static ResultResponse successResponse(String message) {
        return getSuccessResultResponse(message, null);
    }

    /**
     * 自定义返回数据
     *
     * @param data 自定义返回数据
     * @return resultResponse
     */
    public static ResultResponse successResponse(Object data) {

        return getSuccessResultResponse(null, data);
    }

    /**
     * 自定义返回数据
     *
     * @param data    自定义返回数据
     * @param message 信息
     * @return resultResponse
     */
    public static ResultResponse successResponse(String message, Object data) {
        return getSuccessResultResponse(message, data);
    }

    /**
     * 默认失败
     *
     * @return resultResponse
     */
    public static ResultResponse failResponse() {
        return getFailResultResponse(null, null, null);
    }

    /**
     * 自定义失败
     *
     * @param message 信息
     * @param code    状态
     * @return resultResponse
     */
    public static ResultResponse failResponse(Integer code, String message) {
        return getFailResultResponse(code, message, null);
    }

    /**
     * 自定义失败
     *
     * @param message 信息
     * @param code    状态
     * @param data    数据
     * @return resultResponse
     */
    public static ResultResponse failResponse(Integer code, String message, Object data) {
        return getFailResultResponse(code, message, data);
    }

    /**
     * 获取成功的resultResponse
     *
     * @param message 信息
     * @param data    数据
     * @return resultResponse
     */
    private static ResultResponse<Object> getSuccessResultResponse(String message, Object data) {
        ResultResponse<Object> objectResultResponse = new ResultResponse<>();
        if (!StringUtils.isEmpty(message)) {
            objectResultResponse.setMessage(message);
        }
        objectResultResponse.setData(data);
        return objectResultResponse;
    }

    /**
     * 获取失败的resultResponse
     *
     * @param code    状态码
     * @param message 信息
     * @param data    数据
     * @return resultResponse
     */
    private static ResultResponse<Object> getFailResultResponse(Integer code, String message, Object data) {
        ResultResponse<Object> objectResultResponse = new ResultResponse<>();
        objectResultResponse.setSuccess(false);
        if (!StringUtils.isEmpty(message)) {
            objectResultResponse.setMessage(message);
        } else {
            objectResultResponse.setMessage("FALSE");
        }
        if (!StringUtils.isEmpty(code)) {
            objectResultResponse.setCode(code);
        } else {
            objectResultResponse.setCode(4000);
        }
        objectResultResponse.setData(data);

        return objectResultResponse;
    }

}
