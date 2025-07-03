package org.example.collectionandrecommend.demos.web.service;

import com.github.pagehelper.PageInfo;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventFilterDto;
import org.example.collectionandrecommend.demos.web.model.vo.EventVo;

import java.util.List;

public interface EventService {
    void add(EventDto eventDto) throws CustomException;

    void addCateForEvent(int eventId, List<Integer> categoryIds);

    PageInfo<EventVo> eventFilter(EventFilterDto eventFilterDto,int pageNum, int pageSize) throws CustomException;

    void update(EventDto eventDto);

    EventVo getById(Integer id);
}
