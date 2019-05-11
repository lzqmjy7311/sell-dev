package com.imooc.selldev.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/28 11:09 AM
 * @description ：com.imooc.selldev.form
 * @function ：
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;
    @NotEmpty(message = "电话必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "openid必填")
    private String openid;
//    @NotEmpty(message = "购物车不能为空")
    private String items;

}
