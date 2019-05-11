package com.imooc.selldev.service.impl;

import com.imooc.selldev.dataobject.OrderDetail;
import com.imooc.selldev.dto.OrderDTO;
import com.imooc.selldev.enums.OrderStatusEnum;
import com.imooc.selldev.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 4:15 PM
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "111";

    private final String ORDER_ID = "1548652665966749034";

    @Test
    public void createOrder() {

        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("刘召奇");
        orderDTO.setBuyerPhone("18108514700");
        orderDTO.setBuyerAddress("上海市");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        final ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        final OrderDetail o1 = new OrderDetail();
        o1.setProductId("654321");
        o1.setProductQuantity(1);
        orderDetails.add(o1);
//        final OrderDetail o2 = new OrderDetail();
//        o2.setProductId("123456");
//        o2.setProductQuantity(2);
//        orderDetails.add(o2);

        orderDTO.setOrderDetailList(orderDetails);
        final OrderDTO result = this.orderService.createOrder(orderDTO);

        log.info("【创建订单】result={}", result);
    }

    @Test
    public void findOne() {

        final OrderDTO result = this.orderService.findOne(ORDER_ID);
        final List<OrderDetail> orderDetailList = result.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            System.out.println("orderDetail = " + orderDetail);
        }

        log.info("【查询订单】result={}", result);
    }

    @Test
    public void findList() {

        final Page<OrderDTO> result = this.orderService.findList(BUYER_OPENID, PageRequest.of(0, 2));
        Assert.assertNotEquals(0,result.getTotalElements());
        log.info("【查询订单，分页结果集】result={}", result);
    }

    @Test
    public void cancelOrder() {

        final OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        final OrderDTO result = this.orderService.cancelOrder(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());

    }

    @Test
    public void finishOrder() {

        final OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        final OrderDTO result = this.orderService.finishOrder(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paymentOrder() {

        final OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        final OrderDTO result = this.orderService.paymentOrder(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void findSellerList() {

        final PageRequest pageRequest = PageRequest.of(1, 10);
        final Page<OrderDTO> list = this.orderService.findList(pageRequest);
        Assert.assertNotEquals(0, list.getTotalElements());
    }
}