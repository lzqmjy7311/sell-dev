package com.imooc.selldev.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-01-29 19:33
 * @description ：com.imooc.selldev.controller
 * @function ：
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WxController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {

        log.info("进去 auth 方法 ......code={}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx95e6aafddbdff3ee&secret=8e6ed603ec57aaee7bb7d2d690f03dcc&code=" + code + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        final String template = restTemplate.getForObject(url, String.class);
        log.info("result={}", template);
    }

}
