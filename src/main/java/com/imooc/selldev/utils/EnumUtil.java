package com.imooc.selldev.utils;

import com.imooc.selldev.enums.CodeEnum;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 18:28
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
