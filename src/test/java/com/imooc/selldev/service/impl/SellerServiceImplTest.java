package com.imooc.selldev.service.impl;

import com.imooc.selldev.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 11:50
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    public void findSellerInfoByOpenid() {

        final SellerInfo sellerInfo = this.sellerService.findSellerInfoByOpenid("123456");
        Assert.assertNotNull(sellerInfo);
    }

    @Test
    public void test(){

        int[] arr = {35, 53, 43, 1, 3, 5, 98, 6, 32, 52, 81, 32, 44, 66, 11, 23};

        List<Integer> t1 = new ArrayList<>();
        List<Integer> t2 = new ArrayList<>();
        for (int i = 0; i <= arr.length - 1; i++) {
            if (arr[i] / 2 == 0) {
                t1.add(arr[i]);
            } else {
                t2.add(arr[i]);
            }
        }
        Collections.sort(t1);
        Collections.sort(t2);
        System.out.println("偶数：" + t1);
        System.out.println("奇数：" + t2);
    }
}