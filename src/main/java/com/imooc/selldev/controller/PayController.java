package com.imooc.selldev.controller;

import com.imooc.selldev.dto.OrderDTO;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.service.OrderService;
import com.imooc.selldev.service.ProductService;
import com.imooc.selldev.service.impl.OrderServiceImpl;
import com.imooc.selldev.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 12:55
 * @description ：com.imooc.selldev.controller
 * @function ：买家支付
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private PayServiceImpl payService;

    /**
     * 支付
     *
     * @param orderId
     * @param returnUrl
     */
    @GetMapping("/create")
    public ModelAndView createPayment(@RequestParam("orderId") String orderId,
                                      @RequestParam("returnUrl") String returnUrl) {

        if (StringUtils.isEmpty(orderId)) {
            log.info("【微信支付】订单号不能为空");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 1.查询订单
        final OrderDTO orderDTO = this.orderService.findOne(orderId);
        if (orderDTO == null) {
            log.info("【微信支付】订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2.发起支付
        final PayResponse payResponse = this.payService.createPayment(orderDTO);
        final Map<String, Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    /**
     * 微信异步通知
     *
     * @param notifyData
     */
    @PostMapping("notify")
    public ModelAndView wechatAsyncNotify(@RequestBody String notifyData) {

        this.payService.notify(notifyData);
        // 返回微信处理结果
        return new ModelAndView("pay/success");
    }
}











