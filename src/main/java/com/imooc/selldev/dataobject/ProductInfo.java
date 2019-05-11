package com.imooc.selldev.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.selldev.enums.OrderStatusEnum;
import com.imooc.selldev.enums.ProductStatusEnum;
import com.imooc.selldev.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 7:27 PM
 * @description ：com.imooc.selldev.dataobject
 * @function ：商品类
 */
@Entity // 标记为实体映射类
@DynamicUpdate  // 自动更新时间
@Data // lombok 省去get/set 精简代码
public class ProductInfo {

    /** 商品id. */
    @Id
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
    /** 商品状态, 0上架,1下架.*/
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /** 类目编号. */
//    @Column(name = "category_type")
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}
