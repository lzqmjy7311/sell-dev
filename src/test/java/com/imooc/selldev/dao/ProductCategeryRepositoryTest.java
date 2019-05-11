package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 3:25 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategeryRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private ProductCategoryRepository productGategoryDao;

    @Test
    public void findOneTest() {
        final ProductCategory category = productGategoryDao.findById(1).get();
    }

    @Test
    public void saveCategory() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("变态最爱");
        category.setCategoryType(8);
        final ProductCategory save = productGategoryDao.save(category);
        Assert.assertNotNull(save);
    }

    @Test
    public void updateCategory() {
        ProductCategory category = new ProductCategory();
        category.setCategoryId(1);
        category.setCategoryType(6);
        category.setCategoryName("帅哥最爱");
        final ProductCategory save = productGategoryDao.save(category);
        Assert.assertNotNull(null);
    }

    @Test
    public void ctegoryTypeInTest() {
        List<Integer> categoryTypeList = Arrays.asList(1, 2, 3, 4, 5, 6);
        final List<ProductCategory> byCategoryTypeIn = productGategoryDao.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0, byCategoryTypeIn.size());

    }
}