package com.imooc.selldev.enums;

import lombok.Getter;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 10:13 PM
 * @description ：com.imooc.selldev.enums
 * @function ：
 */
@Getter
public enum PayStatusEnum implements CodeEnum{

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
