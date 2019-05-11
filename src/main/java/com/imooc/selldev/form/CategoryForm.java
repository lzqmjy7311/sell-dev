package com.imooc.selldev.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-02 16:01
 * @description ：com.imooc.selldev.form
 * @function ：
 */
@Data
public class CategoryForm {

    private Integer categoryId;
    @NotNull(message = "类目名不能为空")
    private String categoryName;
    @NotNull(message = "Type不能为空")
    private Integer categoryType;
}
