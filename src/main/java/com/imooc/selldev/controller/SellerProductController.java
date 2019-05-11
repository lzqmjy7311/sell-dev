package com.imooc.selldev.controller;

import com.imooc.selldev.dataobject.ProductCategory;
import com.imooc.selldev.dataobject.ProductInfo;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.form.ProductForm;
import com.imooc.selldev.service.impl.CategoryServiceImpl;
import com.imooc.selldev.service.impl.ProductServiceImpl;
import com.imooc.selldev.utils.KeyUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-01 20:22
 * @description ：com.imooc.selldev.controller
 * @function ：卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * 分页查询商品列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView queryProductList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         Map<String, Object> map) {
        final PageRequest pageRequest = PageRequest.of(page - 1, size);
        final Page<ProductInfo> productInfoPage = this.productService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 上架商品
     *
     * @param productId
     * @param map
     * @return
     */
    // TODO 请求方式可能有问题
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {

        if (StringUtils.isEmpty(productId)) {
            log.info("【卖家端上架商品】productId不能为空");
            throw new SellException(ResultEnum.PRODUCT_ID_NOTNULL);
        }
        try {
            this.productService.onSale(productId);
        } catch (SellException e) {
            log.info("【卖家端上架商品】修改产品状态失败，productId={},e={}", productId, e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 下架商品
     *
     * @param productId
     * @param map
     * @return
     */
    // TODO 请求方式可能有问题
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map) {

        if (StringUtils.isEmpty(productId)) {
            log.info("【卖家端下架商品】productId不能为空");
            throw new SellException(ResultEnum.PRODUCT_ID_NOTNULL);
        }
        try {
            this.productService.offSale(productId);
        } catch (SellException e) {
            log.info("【卖家端下架商品】修改产品状态失败，productId={},e={}", productId, e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 新增或修改
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                                        Map<String, Object> map) {
        // 不为空说明是修改，查询需要修改的产品信息，回显
        if (!StringUtils.isEmpty(productId)) {
            final ProductInfo productInfo = this.productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        // 查询所有的类目
        final List<ProductCategory> categoryList = this.categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);
    }

    /**
     * 保存商品
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView saveProduct(@Valid ProductForm productForm,
                                    BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(productForm.getProductId())) {
                productInfo = this.productService.findOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.generateUniquePrimaryKey());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            this.productService.save(productInfo);
        } catch (SellException e) {
            log.info("【卖家端保存商品】保存商品失败，productForm={},e={}", productForm, e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        } catch (Exception e) {
            log.info("【卖家端保存商品】图片地址过长，无法执行={}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}

















