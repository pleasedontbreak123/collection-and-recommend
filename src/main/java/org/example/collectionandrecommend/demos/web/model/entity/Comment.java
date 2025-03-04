package org.example.collectionandrecommend.demos.web.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "评论帖子")
public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer commentParentId;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;
}
