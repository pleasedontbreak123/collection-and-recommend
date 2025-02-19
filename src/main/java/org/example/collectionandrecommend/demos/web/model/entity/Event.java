package org.example.collectionandrecommend.demos.web.model.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
/*
* 在 springdoc-openapi 中，@Schema 注解代替了 @ApiModel 和 @ApiModelProperty，用于描述模型和属性。
* */

@Data
@Schema(description = "赛事信息")
public class Event {

    private int eventId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String organizer;

    public enum EventStatus {
        UPCOMING,
        ONGOING,
        FINISHED,
        DELETED
    }
    private EventStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

