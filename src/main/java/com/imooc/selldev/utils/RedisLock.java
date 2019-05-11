package com.imooc.selldev.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-09 20:01
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间 + 过期时间
     * @return
     */
    public boolean lock(String key, String value) {

        // SETNX 加锁
        final Boolean result = this.redisTemplate.opsForValue().setIfAbsent(key, value);
        if (result == true) {
            return true;
        }
        // 获取redis中的时间
        final String redisCurrentTime = this.redisTemplate.opsForValue().get(key);
        // 判断是否过期超时
        if (!StringUtils.isEmpty(redisCurrentTime) && Long.parseLong(redisCurrentTime) < System.currentTimeMillis()) {
            // 如果被其他线程设置了新的值，则第二个条件判断是过不去的
            final String oldValue = this.redisTemplate.opsForValue().getAndSet(key, value);
            // [分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
            if (!StringUtils.isEmpty(oldValue) && redisCurrentTime.equals(oldValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unLock(String key, String value) {

        final String redisTime = this.redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(redisTime) && redisTime.equals(value)) {
            this.redisTemplate.opsForValue().getOperations().delete(key);
        }
    }
}
