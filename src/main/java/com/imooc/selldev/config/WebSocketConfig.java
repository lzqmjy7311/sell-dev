package com.imooc.selldev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-09 13:55
 * @description ：com.imooc.selldev.config
 * @function ：
 */
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
