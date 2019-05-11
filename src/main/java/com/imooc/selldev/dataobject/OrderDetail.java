package com.imooc.selldev.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 10:21 PM
 * @description ：com.imooc.selldev.dataobject
 * @function ：订单详情表
 */
@Entity
@DynamicUpdate
@Data
public class OrderDetail {

    @Id
    private String detailId;
    /** 订单id. */
    private String orderId;
    /** 商品id. */
    private String productId;
    /** 商品名称. */
    private String productName;
    /** 商品价格. .*/
    private BigDecimal productPrice;
    /** 商品数量. */
    private Integer productQuantity;
    /** 小图. */
    private String productIcon;
    /** 创建时间. */
    private Date createTime;
    /** 更新时间. */
    private Date updateTime;

}
