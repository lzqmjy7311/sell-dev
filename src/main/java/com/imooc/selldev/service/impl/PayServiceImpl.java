package com.imooc.selldev.service.impl;

import com.imooc.selldev.dto.OrderDTO;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.service.PayService;
import com.imooc.selldev.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 13:17
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    private static final String ORDER_NAME = "微信点餐系统订单";

    /**
     * 创建支付
     *
     * @param orderDTO 订单号
     */
    @Override
    public PayResponse createPayment(OrderDTO orderDTO) {

        // 构造支付请求需要的参数
        final PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，request={}", payRequest);

        // 执行支付
        final PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，response={}", payResponse);
        return payResponse;
    }

    /**
     * 异步通知
     *
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {

        final PayResponse payResponse = this.bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，PayRespinse={}", payResponse);
        // 1.查询订单
        final OrderDTO orderDTO = this.orderService.findOne(payResponse.getOrderId());
        if (orderDTO == null) {
            log.info("【微信通知】异步通知，订单不存在，OrderId={}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2.判断订单金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.info("【微信支付】异步通知，订单金额不一致，orderId={},微信支付金额={},系统金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WECHAT_NOTIFY_ORDER_AMOUNT_WRONG);
        }
        // 修改订单的支付状态
        this.orderService.paymentOrder(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     *
     * @param orderDTO
     */
    @Override
    public RefundRequest refund(OrderDTO orderDTO) {

        final RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", refundRequest);
        final RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", refundResponse);

        return refundRequest;
    }
}











