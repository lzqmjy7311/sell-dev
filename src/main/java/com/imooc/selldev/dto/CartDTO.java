package com.imooc.selldev.dto;

import lombok.Getter;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 3:31 PM
 * @description ：com.imooc.selldev.dto
 * @function ：
 */
@Getter
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
