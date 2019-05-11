package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 11:17 AM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save() {

        final OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("129853");
        orderMaster.setBuyerName("离诺诺");
        orderMaster.setBuyerPhone("181085514701");
        orderMaster.setBuyerAddress("上海市浦东新区");
        orderMaster.setBuyerOpenid("111");
        orderMaster.setOrderAmount(new BigDecimal(93.7));

        final OrderMaster result = this.repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {

        final OrderMaster orderMaster = this.repository.findById("123453").get();
        Assert.assertNotNull(orderMaster);
    }

    @Test
    public void findOpenIdAndPageResult() {

        final PageRequest pageRequest = PageRequest.of(0,2);
        final Page<OrderMaster> orderMasterPage = this.repository.findByBuyerOpenid("111", pageRequest);
        Assert.assertNotEquals(0, orderMasterPage.getTotalElements());
    }

    @Test
    public void findByBuyerOpenid() {
    }
}