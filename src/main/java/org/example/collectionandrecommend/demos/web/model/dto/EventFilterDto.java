package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventFilterDto {
    private Integer categoryId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String keywords;
}
