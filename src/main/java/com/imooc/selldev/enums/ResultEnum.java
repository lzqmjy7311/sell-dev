package com.imooc.selldev.enums;

import lombok.Getter;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 1:58 PM
 * @description ：com.imooc.selldev.enums
 * @function ：
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),
    PRODUCT_NOT_EXIST(10, "产品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERRROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    STOCK_UPDATE_FAIL(17, "库存更新失败"),
    ORDER_PAY_STATUS_ERROR(18, "订单支付状态不正确"),
    ORDER_CREATE_ERROR(19, "订单创建失败"),
    CART_NOT_EMPIY(20, "购物车不能为空"),
    OPENID_NOT_EMPIY(21, "openID不能为空"),
    ORDER_OWNER_ERROR(22, "该订单不属于当前用户"),
    ORDER_CANCEL_ERROR(23, "订单取消失败"),
    WECHAT_MP_ERROR(24, "微信公众号方面异常"),
    WECHAT_NOTIFY_ORDER_AMOUNT_WRONG(25, "微信异步通知订单金额效验不通过"),
    ORDER_CANCEL_SUCCESS(26, "订单取消成功"),
    ORDER_FINISH_SUCCESS(26, "订单完结成功"),
    PRODUCT_STATUS_ERROR(27, "商品状态不正确"),
    PRODUCT_ID_NOTNULL(28, "产品id不能为空"),
    PRODUCT_SAVE_ERROR(29, "商品保存失败"),
    CATEGORY_SAVE_ERROR(30, "类目保存失败"),
    LOGIN_FAIL(31, "登录失败，登录信息有误"),
    LOGOUT_SUCCESS(32, "登出成功"),
    LOGOUT_FAIL(33, "登出失败"),

    ;

    private Integer code;
    private String messages;

    ResultEnum(Integer code, String messages) {
        this.code = code;
        this.messages = messages;
    }
}
