package com.imooc.selldev.controller;

import com.imooc.selldev.dto.OrderDTO;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 16:30
 * @description ：com.imooc.selldev.controller
 * @function ：卖家端订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 订单列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView queryOrderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        final PageRequest pageRequest = PageRequest.of(page - 1, size);
        final Page<OrderDTO> orderDTOPage = this.orderService.findList(pageRequest);
        final Map<String, Object> map = new HashMap<>();
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancelOrder(@RequestParam("orderId") String orderId,
                                    Map<String, Object> map) {
        // 先查
        try {
            final OrderDTO orderDTO = this.orderService.findOne(orderId);
            // 执行取消
            this.orderService.cancelOrder(orderDTO);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.info("【卖家端取消订单】发送异常{}", e);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessages());
        map.put("url", "/sell/seller/order/list");
        log.info("【卖家端取消订单】取消订单成功");
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView queryOrderDetails(@RequestParam("ordierId") String orderId,
                                          Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = this.orderService.findOne(orderId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.info("【卖家端查询订单详情】发送异常{}", e);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessages());
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 卖家端完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finishedOrder(@RequestParam("ordierId") String orderId,
                                 Map<String, Object> map) {
        try {
            final OrderDTO orderDTO = this.orderService.findOne(orderId);
            this.orderService.finishOrder(orderDTO);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.info("【卖家端完结订单】发送异常{}", e);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessages());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("order/success", map);
    }
}

















