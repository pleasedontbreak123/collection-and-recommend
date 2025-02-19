package org.example.collectionandrecommend.demos.web.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.collectionandrecommend.demos.web.model.entity.EventImage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "向前端返回的event类")
public class EventVo {

    private int eventId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String organizer;

    private List<Integer> categoryList;

    private List<EventImage> imageList;


}
