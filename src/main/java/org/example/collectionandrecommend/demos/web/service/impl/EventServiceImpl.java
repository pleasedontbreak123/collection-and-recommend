package org.example.collectionandrecommend.demos.web.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSessionException;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.entity.Event;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;

    @Override
    public void add(EventDto eventDto) throws CustomException {
        if (eventDto.getTitle() == null || eventDto.getTitle().isEmpty()){
            throw new CustomException(400,"赛事名不能为空");
        }
        Event event = new Event();
        BeanUtils.copyProperties(eventDto,event);
        event.setStatus(Event.EventStatus.UPCOMING);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());

       // System.out.println(event.toString());
        eventMapper.add(event);



    }

    @Override
    public void addCateForEvent(int eventId, List<Integer> categoryIds) {
        if(eventId == 0){
            throw new CustomException(400,"赛事id不能为空");
        }

        try {
            eventMapper.addCateForEvent(eventId,categoryIds,LocalDateTime.now());
        }catch (SqlSessionException e){
            throw new CustomException(409, "该事件和分类组合已存在");
        }catch (Exception e){
            throw new CustomException(500, "服务器内部错误");
        }

    }
}
