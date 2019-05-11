package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 12:07 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {

        final OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("213123");
        orderDetail.setOrderId("452352");
        orderDetail.setProductId("3252352");
        orderDetail.setProductName("麻辣香锅");
        orderDetail.setProductPrice(new BigDecimal(34.4));
        orderDetail.setProductIcon("http://www.xfgesf.jpg");
        orderDetail.setProductQuantity(1);
        final OrderDetail save = this.repository.save(orderDetail);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOrderId() {

        final List<OrderDetail> byOrderId = this.repository.findByOrderId("452352");
        Assert.assertNotEquals(0, byOrderId.size());
    }
}