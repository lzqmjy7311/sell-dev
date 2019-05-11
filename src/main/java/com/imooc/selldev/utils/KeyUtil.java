package com.imooc.selldev.utils;

import java.util.Random;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 2:21 PM
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
public class KeyUtil {

    /**
     * 生成唯一的id
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String generateUniquePrimaryKey() {

        final Random random = new Random();
        final Integer r = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(r);
    }
}
