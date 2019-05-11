package com.imooc.selldev.utils;

import org.apache.coyote.OutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-14 02:03
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
public class LockUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public Boolean lcok(String key, String value) {

        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        final String currentTime = this.redisTemplate.opsForValue().get(key);

        if (!StringUtils.isEmpty(currentTime) && Long.valueOf(currentTime) < System.currentTimeMillis()) {

            // 获取redis中的时间
            final String newCurrentTIme = this.redisTemplate.opsForValue().getAndSet(key, value);

            if (!StringUtils.isEmpty(newCurrentTIme) && Long.valueOf(newCurrentTIme).equals(currentTime)) {

                return true;
            }
        }

        return false;
    }

    public void unLock(String key, String value) {

        final String redisCurrentTime = this.redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(redisCurrentTime) && redisCurrentTime.equals(value)) {
            this.redisTemplate.opsForValue().getOperations().delete(key);
        }
    }
}



















