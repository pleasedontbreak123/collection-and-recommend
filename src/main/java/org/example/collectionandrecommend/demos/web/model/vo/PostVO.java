package org.example.collectionandrecommend.demos.web.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "向前端返回的post类")
public class PostVO {

    private Integer postId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;

}
