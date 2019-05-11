package com.imooc.selldev.enums;

import lombok.Getter;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 9:36 PM
 * @description ：com.imooc.selldev.enums
 * @function ：
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{

    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
