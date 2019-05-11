package com.imooc.selldev.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 3:37 PM
 * @description ：com.imooc.selldev.VO
 * @function ：商品详情
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = 321511687080035585L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

}
