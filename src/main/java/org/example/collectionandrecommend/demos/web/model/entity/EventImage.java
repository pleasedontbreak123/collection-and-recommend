package org.example.collectionandrecommend.demos.web.model.entity;

import lombok.Data;

@Data
public class EventImage {

    private int imageId;

    private int eventId;

    private String imageUrl;

    enum ImageType{
        MAIN,
        LOGO,
        OTHER
    }

    private ImageType imageType;
}
