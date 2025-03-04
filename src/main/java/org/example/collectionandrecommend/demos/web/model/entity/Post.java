package org.example.collectionandrecommend.demos.web.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "帖子信息")
public class Post {
    private Integer postId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
}
