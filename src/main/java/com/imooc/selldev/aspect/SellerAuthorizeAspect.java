package com.imooc.selldev.aspect;

import com.imooc.selldev.constent.RedisConstent;
import com.imooc.selldev.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 16:09
 * @description ：com.imooc.selldev.aspect
 * @function ：
 */



@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 配置切入点
    @Pointcut("execution(public * com.imooc.selldev.controller.Seller*.*(..))" +
            "&& !execution(public * com.imooc.selldev.controller.SellerUserController.*(..))")
    public void verify() {}

    // 配置切入时机 verify切入点之前
    @Before("verify()")
    public void doVerify() {
        log.warn("【登录效验】执行登录验证......");
        // 1.获取请求者的上下文
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = servletRequestAttributes.getRequest();
        // 2.获取cookie
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.warn("【登录效验】cookies为空");
            throw new SellerAuthorizeException();
        }
        // 3.遍历
        Cookie cookie = null;
        for (Cookie scookie : cookies) {
            if (scookie.getName().equals("token")) {
                cookie = scookie;
            }
        }
        if (cookie == null) {
            log.warn("【登录效验】cookie中查找不到token");
            throw new SellerAuthorizeException();
        }
        // 4.查询redis
        final String token = this.redisTemplate.opsForValue().get(String.format(RedisConstent.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(token)) {
            log.warn("【登录效验】redis中查找不到token");
            throw new SellerAuthorizeException();
        }
    }
}
