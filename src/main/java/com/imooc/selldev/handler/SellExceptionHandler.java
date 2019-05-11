package com.imooc.selldev.handler;

import com.imooc.selldev.config.UrlConfig;
import com.imooc.selldev.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 17:16
 * @description ：com.imooc.selldev.handler
 * @function ：统一处理异常
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private UrlConfig urlConfig;

    // http://selllzq.natapp1.cc/sell/wechat/qrAuthorize?returnUrl=http://selllzq.natapp1.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {

        return new ModelAndView("redirect:"
                .concat(urlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(urlConfig.getSell())
                .concat("/sell/seller/login"));

    }
}
