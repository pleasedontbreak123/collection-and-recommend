package org.example.collectionandrecommend.demos.web.utils.redisUtil;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
