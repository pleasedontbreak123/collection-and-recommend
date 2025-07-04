package org.example.collectionandrecommend.demos.web.utils.redisUtil;



import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

//全局id生成器
@Component
public class RedisIdWorker {

    private static final long BEGIN_TIMESTAMP = 1744588800L;

    private final StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String prefix) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1获取当前日期，精确到天

        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM;dd"));
        // 2.2获取当前日期的序列号
        long count = stringRedisTemplate.opsForValue().increment("icr:" + prefix + ":" + date);

        // 3.拼接并返回
        return timestamp << 32 | count;


    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 4, 14, 0, 0, 0);
        long second = localDateTime.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second);
    }
}
