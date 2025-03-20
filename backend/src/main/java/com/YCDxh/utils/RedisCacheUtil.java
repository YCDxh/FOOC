package com.YCDxh.utils;

import com.alibaba.fastjson.JSON;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class RedisCacheUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    // 在 RedisCacheUtil 中添加带锁的检查方法：
    public boolean hasKeyWithLock(String key) {
        RLock lock = redissonClient.getLock("lock:" + key);
        try {
            lock.lock();
            return redisTemplate.hasKey(key);
        } finally {
            lock.unlock();
        }
    }


    // 在 RedisCacheUtil.java 中添加以下方法：
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    public <T> T getFromCacheOrDB(
            String keyPrefix,
            Long dbId,
            Supplier<T> dbSupplier,
            long expireTime
    ) {
        return getFromCacheOrDBWithLock(keyPrefix, dbId, dbSupplier, expireTime, null);
    }

    public <T> T getFromCacheOrDBWithLock(
            String keyPrefix,
            Long dbId,
            Supplier<T> dbSupplier,
            long expireTime,
            Class<T> clazz // 新增参数：目标类型
    ) {
        String key = keyPrefix + ":" + dbId;
        RLock lock = redissonClient.getLock("lock:" + key);

        try {
            boolean isLocked = lock.tryLock(0, 1, TimeUnit.SECONDS);
            if (!isLocked) {
                Object cached = redisTemplate.opsForValue().get(key);
                return cached != null ? JSON.parseObject(cached.toString(), clazz) : null;
            }

            // 尝试从 Redis 获取
            Object cached = redisTemplate.opsForValue().get(key);
            if (cached != null) {
                return JSON.parseObject(cached.toString(), clazz);
            }

            // 数据库查询
            T result = dbSupplier.get();
            if (result != null) {
                String json = JSON.toJSONString(result);
                redisTemplate.opsForValue().set(key, json, expireTime, TimeUnit.SECONDS);
            }
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
