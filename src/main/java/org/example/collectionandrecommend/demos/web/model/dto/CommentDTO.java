package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Integer userId;
    private Integer commentParentId;
    private String content;
    private Long postId;
}
