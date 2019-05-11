package com.imooc.selldev.service;

import com.imooc.selldev.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 12:15 PM
 * @description ：com.imooc.selldev.service
 * @function ：
 */
public interface OrderService {

    /** 创建订单. */
    OrderDTO createOrder(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查询订单列表. */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单. */
    OrderDTO cancelOrder(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finishOrder(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO paymentOrder(OrderDTO orderDTO);

    /** 卖家端订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

}
