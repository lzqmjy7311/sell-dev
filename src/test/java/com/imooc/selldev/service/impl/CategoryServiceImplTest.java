package com.imooc.selldev.service.impl;

import com.imooc.selldev.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 7:02 PM
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        final ProductCategory one = this.categoryService.findOne(1);
        Assert.assertNotNull(one);
    }

    @Test
    public void findAll() {
        final List<ProductCategory> all = this.categoryService.findAll();
        all.forEach(productCategory -> System.out.println(productCategory));
        Assert.assertNotEquals(0, all.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        final List<Integer> integers = Arrays.asList(1, 2, 3, 5, 6, 7, 4);
        final List<ProductCategory> byCategoryTypeIn = this.categoryService.findByCategoryTypeIn(integers);
        Assert.assertNotEquals(0, byCategoryTypeIn.size());
    }

    @Test
    public void save() {
        final ProductCategory category = new ProductCategory();
        category.setCategoryId(5);
        category.setCategoryType(1);
        category.setCategoryName("美女最爱");
        final ProductCategory save = this.categoryService.save(category);
        Assert.assertNotNull(save);

    }
}