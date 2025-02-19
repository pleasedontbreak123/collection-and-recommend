package org.example.collectionandrecommend.demos.web.service;

import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;

import java.util.List;

public interface EventService {
    void add(EventDto eventDto) throws CustomException;

    void addCateForEvent(int eventId, List<Integer> categoryIds);
}
