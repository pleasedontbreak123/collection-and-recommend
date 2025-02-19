package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;
import org.example.collectionandrecommend.demos.web.model.entity.EventImage;

@Data
public class EventImageDto {
    private int eventId;

    private String imageUrl;

    enum ImageType{
        MAIN,
        LOGO,
        OTHER
    }

    private ImageType imageType;
}
