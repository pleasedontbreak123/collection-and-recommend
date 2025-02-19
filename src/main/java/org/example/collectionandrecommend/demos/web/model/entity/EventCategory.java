package org.example.collectionandrecommend.demos.web.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "赛事分类实体")
public class EventCategory {

    private int eventCategoryId;

    private String categoryName;

    private LocalDateTime createAt;

    public enum CategoryStatus {
        ACTIVE, // 代表激活状态
        DELETED // 代表未激活状态

    }
    private CategoryStatus status;
}
