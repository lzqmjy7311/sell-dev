package com.imooc.selldev.constent;

import lombok.Data;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 14:20
 * @description ：com.imooc.selldev.constent
 * @function ：
 */
public interface RedisConstent {

    String TOKEN_PREFIX = "TOKEN:token_%s";

    Integer EXPIRE = 7200; // 2小时
}
