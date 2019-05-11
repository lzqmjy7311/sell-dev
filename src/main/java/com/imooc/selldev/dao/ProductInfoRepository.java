package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 8:59 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
