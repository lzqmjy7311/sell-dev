package com.imooc.selldev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.selldev.config.Date2LongSerialize;
import com.imooc.selldev.dataobject.OrderDetail;
import com.imooc.selldev.enums.OrderStatusEnum;
import com.imooc.selldev.enums.PayStatusEnum;
import com.imooc.selldev.utils.EnumUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 12:30 PM
 * @description ：com.imooc.selldev.dto
 * @function ：订单（数据层传输对象）
 */
@Data
public class OrderDTO {

    /** 订单id. */
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
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date createTime;
    /** 订单更新时间. */
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date updateTime;
    /** 订单详情，一个订单可能有多条订单详情. */
    private List<OrderDetail> orderDetailList;

    @JsonIgnore // 忽略
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore // 忽略
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
