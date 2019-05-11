package com.imooc.selldev.form;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-02 13:03
 * @description ：com.imooc.selldev.form
 * @function ：
 */
@Data
public class ProductForm {

    /** 商品id. */
    private String productId;
    /** 商品名称. */
    private String productName;
    /** 商品单价. */
    private BigDecimal productPrice;
    /** 商品库存. */
    private Integer productStock;
    /** 商品描述. */
    private String productDescription;
    /** 商品图片. */
    private String productIcon;
    /** 类目编号. */
    private Integer categoryType;
}

