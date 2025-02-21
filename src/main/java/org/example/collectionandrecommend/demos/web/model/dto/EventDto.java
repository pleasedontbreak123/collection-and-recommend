package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;
import org.example.collectionandrecommend.demos.web.model.entity.Event;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private Integer eventId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String organizer;

    public Event.EventStatus status;
}
