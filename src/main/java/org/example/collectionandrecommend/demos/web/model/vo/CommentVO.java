package org.example.collectionandrecommend.demos.web.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "向前端返回的comment类")
@Data
public class CommentVO {

    private Integer commentId;
    private Integer userId;
    private Integer commentParentId;
    private String content;
    private LocalDateTime createdAt;
    private Integer postId;
}
