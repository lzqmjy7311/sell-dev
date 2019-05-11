package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 11:38
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerinfoRepositoryTest {

    private static final String OPEN_ID = "123456";

    @Autowired
    private SellerinfoRepository repository;

    @Test
    public void save() {

        final SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenId("123456");
        sellerInfo.setUsername("刘召奇");
        sellerInfo.setPassword("123456");
        sellerInfo.setSellerId("123456");
        final SellerInfo save = this.repository.save(sellerInfo);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOpenId() {

        final SellerInfo byOpenId = this.repository.findByOpenId(OPEN_ID);
        Assert.assertNotNull(byOpenId);
    }
}













