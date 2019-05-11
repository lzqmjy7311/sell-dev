package com.imooc.selldev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Map;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-01-29 14:56
 * @description ：com.imooc.selldev.config
 * @function ：
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众平台id
     */
    private String mpAppId;
    /**
     * 公众平台秘钥
     */
    private String mpAppSecret;
    /**
     * 开放平台id
     */
    private String openAppId;
    /**
     * 开放平台秘钥
     */
    private String openAppSecret;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户密钥
     */
    private String mchKey;
    /**
     * 商户证书路径
     */
    private String keyPath;
    /**
     * 微信异步通知地址
     */
    private String notifyUrl;
    /**
     * 模板id
     */
    private Map<String, String> templateId;


}
