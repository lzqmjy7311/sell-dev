package com.imooc.selldev.service.impl;

import com.imooc.selldev.dao.ProductInfoRepository;
import com.imooc.selldev.dataobject.ProductInfo;
import com.imooc.selldev.dto.CartDTO;
import com.imooc.selldev.enums.ProductStatusEnum;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.service.ProductService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 1:54 PM
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        final ProductInfo productInfo = this.repository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        return productInfo;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return this.repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        productInfo = this.repository.save(productInfo);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_SAVE_ERROR);
        }
        return productInfo;
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            final ProductInfo productInfo = this.repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            final Integer stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            if (stock < productInfo.getProductStock()) {
                throw new SellException(ResultEnum.STOCK_UPDATE_FAIL);
            }
            productInfo.setProductStock(stock);
            this.repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            final ProductInfo productInfo = this.repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            final Integer productStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (productStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(productStock);
            this.repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {

        final ProductInfo productInfo = this.repository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return this.repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {

        final ProductInfo productInfo = this.repository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return this.repository.save(productInfo);
    }
}
