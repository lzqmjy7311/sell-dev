package com.imooc.selldev.controller;

import com.imooc.selldev.config.UrlConfig;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-01-29 14:15
 * @description ：com.imooc.selldev.controller
 * @function ：应用授权
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Resource(name = "WxMpService")
    private WxMpService wxMpService;

    @Resource(name = "WxOpenService")
    private WxMpService wxOpenService;

    @Autowired
    private UrlConfig urlConfig;

    /**
     * 网页应用授权
     *
     * @param returnUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/authorize")
    public String wechatAuthorize(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {

        // 1.配置
        // 2.调用方法
        log.info("【网页应用授权】 接收参数：returnUrl={}", returnUrl);
        String url = urlConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        final String redirectUrl = this.wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl, "UTF-8"));
        log.info("【网页应用授权】 转发地址：redirectUrl={}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * 网页应用授权
     *
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {

        log.info("【网页应用授权】code={}", code);
        log.info("【网页应用授权】state={}", returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【网页应用授权】e={}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【网页应用授权】openid={}", openId);
        final String s = "redirect:" + returnUrl + "?openid=" + openId;
        log.info("【网页应用授权】转发地址：s={}", s);
        return s;
    }

    /**
     * 卖家端扫码登录
     *
     * @param returnUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {
        log.info("【卖家扫码登录】接收参数：returnUrl+{}", returnUrl);
        String url = urlConfig.getWechatOpenAuthorize() + "/sell/wechat/orUserInfo";
        final String redirectUrl = this.wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl, "UTF-8"));
        log.info("【卖家扫码登录】转发地址: redirectUrl={}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * 卖家端扫码登录
     *
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/orUserInfo")
    public String orUserinfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
        log.info("【卖家扫码登录】code={}", code);
        log.info("【卖家扫码登录】state={}", returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【卖家扫码登录】e={}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【卖家扫码登录】openid={}", openId);
        final String redirectUrl = "redirect:" + returnUrl + "?openid=" + openId;
        log.info("【卖家扫码登录】转发地址：redirectUrl={}", redirectUrl);
        return redirectUrl;
    }
}








