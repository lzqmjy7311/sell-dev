package com.imooc.selldev.dataobject;

import com.imooc.selldev.enums.OrderStatusEnum;
import com.imooc.selldev.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 9:25 PM
 * @description ：com.imooc.selldev.dataobject
 * @function ： 订单表
 */
@Entity
@DynamicUpdate
@Data
public class OrderMaster {

    /** 订单id. */
    @Id
    private String orderId;
    /** 买家名称. */
    private String buyerName;
    /** 买家电话. */
    private String buyerPhone;
    /** 买家地址. */
    private String buyerAddress;
    /** 买家微信openID. */
    private String buyerOpenid;
    /** 订单总金额. */
    private BigDecimal orderAmount;
    /** 订单状态，默认0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态，默认0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /** 订单创建时间. */
    private Date createTime;
    /** 订单更新时间. */
    private Date updateTime;

}
