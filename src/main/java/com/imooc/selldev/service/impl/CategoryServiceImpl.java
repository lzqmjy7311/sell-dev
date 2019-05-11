package com.imooc.selldev.service.impl;

import com.imooc.selldev.dao.ProductCategoryRepository;
import com.imooc.selldev.dataobject.ProductCategory;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 5:40 PM
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return this.repository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return this.repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        final ProductCategory category = this.repository.save(productCategory);
        if (category == null) {
            throw new SellException(ResultEnum.CATEGORY_SAVE_ERROR);
        }
        return category;
    }
}
