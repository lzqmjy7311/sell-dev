package com.imooc.selldev.service;

import com.imooc.selldev.dataobject.ProductInfo;
import com.imooc.selldev.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 1:47 PM
 * @description ：com.imooc.selldev.service
 * @function ：商品列表
 */
public interface ProductService {

    /** 根据商品id，查询商品. */
    ProductInfo findOne(String productId);

    /** 查询所有上架的商品. */
    List<ProductInfo> findUpAll();

    /** 分页查询所有商品. */
    Page<ProductInfo> findAll(Pageable pageable);

    /** 添加商品. */
    ProductInfo save(ProductInfo productInfo);

    /** 加库存. */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存. */
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 上架. */
    ProductInfo onSale(String productId);

    /** 下架. */
    ProductInfo offSale(String productId);

}
