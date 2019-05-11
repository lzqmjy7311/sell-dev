package com.imooc.selldev.exception;

import com.imooc.selldev.enums.ResultEnum;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 1:58 PM
 * @description ：com.imooc.selldev.exception
 * @function ：
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessages());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;

    }
}
