package org.example.collectionandrecommend.demos.web.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFavor {
    private Integer id;
    private Integer userId;
    private Integer eventId;
    private LocalDateTime createdAt;

}
