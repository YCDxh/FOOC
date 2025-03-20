package com.YCDxh.service;


import com.YCDxh.model.entity.Course;
import com.YCDxh.repository.CourseRepository;
import com.YCDxh.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseImageService {

    private final MinioUtil minioUtil;
    private final RedisTemplate<String, byte[]> redisTemplate;
    private final CourseRepository courseRepository;

    // 缓存键前缀
    private static final String THUMBNAIL_KEY_PREFIX = "course:thumbnail:";

    // 分布式锁前缀
    private static final String LOCK_KEY_PREFIX = "lock:course:thumbnail:";

    // 压缩后的图片尺寸
    private static final int THUMBNAIL_WIDTH = 200;
    private static final int THUMBNAIL_HEIGHT = 200;

    /**
     * 根据课程ID获取缩略图（优先从Redis缓存）
     */
    public byte[] getThumbnail(Long courseId) {
        String key = getKey(courseId);
        byte[] thumbnail = redisTemplate.opsForValue().get(key);
        if (thumbnail != null) {
            return thumbnail;
        }

        // 尝试加锁，避免重复下载和压缩
        String lockKey = LOCK_KEY_PREFIX + courseId;
        if (tryLock(lockKey, 10, TimeUnit.SECONDS)) {
            try {
                thumbnail = generateThumbnail(courseId);
                if (thumbnail != null) {
                    // 设置缓存（30分钟过期）
                    redisTemplate.opsForValue().set(key, thumbnail, 30, TimeUnit.MINUTES);
                }
            } finally {
                redisTemplate.delete(lockKey);
            }
        }

        // 如果仍未获取到，返回原始图片或默认图
        if (thumbnail == null) {
            thumbnail = minioUtil.downloadAsBytes(courseId + ".jpg"); // 假设文件名与courseId一致
        }
        return thumbnail;
    }

    private boolean tryLock(String lockKey, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, "locked".getBytes(), timeout, unit));
        // return redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", timeout, unit);
    }

    private byte[] generateThumbnail(Long courseId) {
        // 1. 从数据库获取课程详情（包含MinIO的coverUrl）
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return null;
        }

        // 2. 从MinIO下载原始图片
        byte[] original = minioUtil.downloadAsBytes(course.getCoverUrl());
        if (original == null) {
            return null;
        }

        // 3. 压缩图片
        try (InputStream in = new ByteArrayInputStream(original)) {
            BufferedImage image = ImageIO.read(in);
            BufferedImage thumbnail = Thumbnails.of(image)
                    .size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                    .asBufferedImage();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "jpg", out);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("图片压缩失败", e);
            return null;
        }
    }

    private String getKey(Long courseId) {
        return THUMBNAIL_KEY_PREFIX + courseId;
    }
}
