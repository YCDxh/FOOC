package com.YCDxh.service;

import com.YCDxh.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioUtil minioUtil;
    private final RedisTemplate<String, byte[]> redisTemplate;

    public boolean storeCompressedImageToRedis(String objectName, String redisKey) {
        byte[] compressedBytes = minioUtil.compressImage(objectName, 800, 600);
        if (compressedBytes == null) {
            return false;
        }

        // 存入Redis并设置过期时间（例如1小时）
        redisTemplate.opsForValue().set(redisKey, compressedBytes, 1, TimeUnit.HOURS);
        return true;
    }

    public byte[] getCompressedImageFromRedis(String redisKey) {
        return redisTemplate.opsForValue().get(redisKey);
    }
}
