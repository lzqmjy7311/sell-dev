package com.imooc.selldev.controller;

import com.imooc.selldev.VO.ProductInfoVO;
import com.imooc.selldev.VO.ProductVO;
import com.imooc.selldev.VO.ResultVO;
import com.imooc.selldev.dataobject.ProductCategory;
import com.imooc.selldev.dataobject.ProductInfo;
import com.imooc.selldev.service.impl.CategoryServiceImpl;
import com.imooc.selldev.service.impl.ProductServiceImpl;
import com.imooc.selldev.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 3:06 PM
 * @description ：com.imooc.selldev.controller
 * @function ：买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductServiceImpl productInfoService;

    @Autowired
    private CategoryServiceImpl categoryService;

//    @GetMapping("/calist")
//    public ResultVO caList() {
//
//        // 1.查询所有上架商品
//        final List<ProductInfo> productInfoList = this.productInfoService.findUpAll( );
//
//        // 2.查询类目
//        // 获取所有上架商品的类目编号
//        final List<Integer> categoryTypes = productInfoList.stream()
//                .map(categoryInfo -> categoryInfo.getCategoryType())
//                .collect(Collectors.toList());
//        this.categoryService.findByCategoryTypeIn(categoryTypes);
//        // 根据类目编号获取获取所有类目信息
//        final List<ProductCategory> categoryList = this.categoryService.findByCategoryTypeIn(categoryTypes);
//
//        // 3.数据的拼接
//        // 声明集合，储存返回给前端的数据
//        final List<ProductVO> productVOList = new ArrayList<>();
//        // 遍历类目集合
//        for (ProductCategory category : categoryList) {
//            final ProductVO productVO = new ProductVO();
//            productVO.setCategoryName(category.getCategoryName());
//            productVO.setCategoryType(category.getCategoryType());
//            final List<ProductInfoVO> productInfoVOList = new ArrayList<>();
//            // 遍历商品集合
//            for (ProductInfo productInfo : productInfoList) {
//                // 类目的Type和商品Type相等，才拼接数据
//                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
//                    final ProductInfoVO productInfoVO = new ProductInfoVO();
//                    BeanUtils.copyProperties(productInfo, productInfoVO);
//                    productInfoVOList.add(productInfoVO);
//                }
//            }
//            productVO.setProductInfoVOList(productInfoVOList);
//            productVOList.add(productVO);
//        }
//        // 封装，返回给前端
//        return ResultVOUtil.success(productVOList);
//    }

    @GetMapping("list")
    public ResultVO List() {

        final List<ProductCategory> categoryList = this.categoryService.findAll();
        final List<ProductInfo> productInfoList = this.productInfoService.findUpAll();

        final ArrayList<ProductVO> productVOS = new ArrayList<>();
        for (ProductCategory category : categoryList) {
            final List<ProductInfoVO> infoArrayList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    final ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    infoArrayList.add(productInfoVO);
                }
            }
            final ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());
            productVO.setProductInfoVOList(infoArrayList);
            productVOS.add(productVO);
        }
        return ResultVOUtil.success(productVOS);
    }
}











