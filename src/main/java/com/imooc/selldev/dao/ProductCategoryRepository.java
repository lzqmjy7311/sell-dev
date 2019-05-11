package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 2:39 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
