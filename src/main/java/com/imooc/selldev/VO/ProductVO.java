package com.imooc.selldev.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 3:29 PM
 * @description ：com.imooc.selldev.VO
 * @function ：商品信息(包含类目)
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 289697705535977528L;

    @JsonProperty("name") // 返回给前端的数据名称
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
