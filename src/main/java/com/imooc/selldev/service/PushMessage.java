package com.imooc.selldev.service;

import com.imooc.selldev.dto.OrderDTO;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 18:25
 * @description ：com.imooc.selldev.service
 * @function ：
 */
public interface PushMessage {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
