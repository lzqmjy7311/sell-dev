package com.imooc.selldev.service.impl;

import com.imooc.selldev.dto.OrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 19:06
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

    @Autowired
    private PushMessageImpl pushMessage;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void orderStatus() {

        final OrderDTO orderDTO = this.orderService.findOne("1549004873239611441");
        this.pushMessage.orderStatus(orderDTO);
    }
}