package com.imooc.selldev.service;

import com.imooc.selldev.dataobject.ProductCategory;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 5:36 PM
 * @description ：com.imooc.selldev.service
 * @function ：
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
