package com.imooc.selldev.service.impl;

import com.imooc.selldev.dataobject.ProductInfo;
import com.imooc.selldev.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 2:05 PM
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {

        final ProductInfo result = this.productService.findOne("123456");
        Assert.assertEquals("123456", result.getProductId());
    }

    @Test
    public void findUpAll() {
        final List<ProductInfo> upProductInfo = this.productService.findUpAll();
        upProductInfo.forEach(productInfo -> System.out.println(productInfo));
        Assert.assertNotEquals("0", upProductInfo.size());
    }

    @Test
    public void findAll() {
        final PageRequest pageRequest = PageRequest.of(1, 2);
        final Page<ProductInfo> productInfoPageResult = this.productService.findAll(pageRequest);
        System.out.println("TotalElements: " + productInfoPageResult.getTotalElements());
        System.out.println("Content: " + productInfoPageResult.getContent());
        System.out.println("TotalPages: " + productInfoPageResult.getTotalPages());
        System.out.println("Number: " + productInfoPageResult.getNumber());
        System.out.println("Size: " + productInfoPageResult.getSize());
        System.out.println("Pageable: " + productInfoPageResult.getPageable());
        System.out.println("InfoPageResult: " + productInfoPageResult.get().count());
        Assert.assertNotEquals(0, productInfoPageResult.get().count());
    }

    @Test
    public void save() {
        final ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("553453");
        productInfo.setProductName("贵阳怪噜饭");
        productInfo.setProductPrice(new BigDecimal(9.8));
        productInfo.setProductStock(103);
        productInfo.setProductDescription("我最爱吃的炒饭");
        productInfo.setProductIcon("https://www.xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(4);
        final ProductInfo productInfoResult = this.productService.save(productInfo);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void onSale() {
        final ProductInfo productInfo = this.productService.onSale("987654");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void offSale() {
        final ProductInfo productInfo = this.productService.offSale("987654");
        Assert.assertNotNull(productInfo);
    }
}