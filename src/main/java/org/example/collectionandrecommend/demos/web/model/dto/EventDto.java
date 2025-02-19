package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String organizer;
}
