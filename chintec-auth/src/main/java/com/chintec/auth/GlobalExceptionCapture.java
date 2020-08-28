package com.chintec.auth;

import com.chintec.common.enums.CommonCodeEnum;
import com.chintec.common.exception.ParamsException;
import com.chintec.common.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/6/21 19:20
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionCapture {
    @ExceptionHandler(ParamsException.class)
    public ResultResponse paramException(ParamsException e) {
        log.error(e.getMsg());
        return ResultResponse.failResponse(CommonCodeEnum.PARAMS_ERROR_CODE.getCode(), e.getMsg());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultResponse runtimeException(RuntimeException e) {
        String message = "网络错误，请重试！！！";
        if (e != null) {
            log.error(e.getMessage());
        }
        return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), message);
    }
}
