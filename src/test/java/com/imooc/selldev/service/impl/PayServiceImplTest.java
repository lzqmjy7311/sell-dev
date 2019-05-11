package com.imooc.selldev.service.impl;

import com.imooc.selldev.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 13:48
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Test
    public void createPayment() {

        final OrderDTO orderDTO = new OrderDTO();
        this.payService.createPayment(orderDTO);
        
    }
}