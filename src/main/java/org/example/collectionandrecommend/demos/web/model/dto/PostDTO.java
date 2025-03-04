package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Integer userId;
    private String content;
}
