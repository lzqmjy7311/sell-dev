package com.imooc.selldev.enums;

import lombok.Getter;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 1:58 PM
 * @description ：com.imooc.selldev.enums
 * @function ：商品状态
 */
@Getter
public enum  ProductStatusEnum implements CodeEnum{

    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
