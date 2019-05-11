package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 9:39 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class productInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("987654");
        productInfo.setProductName("花溪羊肉粉");
        productInfo.setProductPrice(new BigDecimal(12.3));
        productInfo.setProductStock(130);
        productInfo.setProductDescription("吃过一次，念念不忘");
        productInfo.setProductIcon("https://www.xxxxxxx.icon");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);
        repository.save(productInfo);
    }

    @Test
    public void findByProductStatus() {

        final List<ProductInfo> result = this.repository.findByProductStatus(0);
        Assert.assertNotEquals(0, result.size());
    }
}