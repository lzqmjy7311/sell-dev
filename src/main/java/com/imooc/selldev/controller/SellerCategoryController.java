package com.imooc.selldev.controller;

import com.imooc.selldev.dataobject.ProductCategory;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.form.CategoryForm;
import com.imooc.selldev.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-02 15:28
 * @description ：com.imooc.selldev.controller
 * @function ：卖家端类目管理
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * 类目列表
     *
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView queryCategoryList(Map<String, Object> map) {

        final List<ProductCategory> categoryList = this.categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        // 不为空说明是修改，查询需要修改的产品信息，回显
        if (categoryId != null) {
            final ProductCategory category = this.categoryService.findOne(categoryId);
            map.put("category", category);
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * 保存/更新
     *
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView saveCagegory(@Valid CategoryForm categoryForm,
                                     BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                productCategory = this.categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            this.categoryService.save(productCategory);
        } catch (SellException e) {
            log.info("【卖家端保存类目】保存失败，categoryForm={}", categoryForm);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("/common/success", map);
    }

}
