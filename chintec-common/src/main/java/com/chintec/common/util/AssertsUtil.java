package com.chintec.common.util;

import com.chintec.common.exception.ParamsException;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/6/19 12:42
 */
public class AssertsUtil {
    public static void isTrue(Boolean t, String message, Integer code) {
        if (t) {
            throw new ParamsException(code, message);
        }
    }

    public static void isTrue(Boolean t, String message) {
        if (t) {
            throw new ParamsException(message);
        }
    }
}
