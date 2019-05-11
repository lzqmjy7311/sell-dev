package com.imooc.selldev.controller;

import com.imooc.selldev.config.UrlConfig;
import com.imooc.selldev.constent.RedisConstent;
import com.imooc.selldev.dataobject.SellerInfo;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.service.impl.SellerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 13:53
 * @description ：com.imooc.selldev.controller
 * @function ：卖家端
 */

@Controller
@RequestMapping("seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录
     * @param openId
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "openid",required = false) String openId,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        if (StringUtils.isEmpty(openId)) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessages());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        // 1.效验openid是否匹配
        final SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openId);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessages());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        // 2.设置token到redis
        final String token = UUID.randomUUID().toString();
        this.redisTemplate.opsForValue().set(String.format(RedisConstent.TOKEN_PREFIX, token), openId, RedisConstent.EXPIRE, TimeUnit.SECONDS);

        // 3.设置token到cookie
        final Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        return new ModelAndView("redirect:" + urlConfig.getSell() + "/sell/seller/order/list");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {

        // 查询cookie
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    // 清除cookie
                    final Cookie cookieOut = new Cookie("token", null);
                    cookieOut.setPath("/");
                    cookieOut.setMaxAge(0);
                    response.addCookie(cookieOut);
                    // 清除redis
                    this.redisTemplate.delete(String.format(RedisConstent.TOKEN_PREFIX, cookie.getValue()));
                }
            }
        } else {
            map.put("msg", ResultEnum.LOGOUT_FAIL.getMessages());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessages());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("/common/success", map);
    }
}
