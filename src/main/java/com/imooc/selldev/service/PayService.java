package com.imooc.selldev.service;

import com.imooc.selldev.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 13:14
 * @description ：com.imooc.selldev.service
 * @function ：
 */
public interface PayService {

    /** 创建支付. */
    PayResponse createPayment(OrderDTO orderDTO);

    /** 异步通知. */
    PayResponse notify(String notifyData);

    /** 退款. */
    RefundRequest refund(OrderDTO orderDTO);
}
