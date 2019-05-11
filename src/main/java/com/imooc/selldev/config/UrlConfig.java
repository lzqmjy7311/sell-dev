package com.imooc.selldev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 13:29
 * @description ：com.imooc.selldev.config
 * @function ：
 */
@Data
@ConfigurationProperties(prefix = "urlconfig")
@Component
public class UrlConfig {

    /**
     * 微信公众平台授权url
     */
    private String wechatMpAuthorize;
    /**
     * 微信开放平台授权url
     */
    private String wechatOpenAuthorize;
    /**
     * 点餐系统url
     */
    private String sell;

}
