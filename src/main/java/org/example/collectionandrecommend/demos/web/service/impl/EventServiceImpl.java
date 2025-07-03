package org.example.collectionandrecommend.demos.web.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionException;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventFilterDto;
import org.example.collectionandrecommend.demos.web.model.entity.Event;
import org.example.collectionandrecommend.demos.web.model.vo.EventVo;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo<EventVo> eventFilter(EventFilterDto eventFilterDto,int pageNum, int pageSize) throws CustomException{

        // 启动分页查询
        PageHelper.startPage(pageNum, pageSize);

        List<EventVo> resList;

        resList = eventMapper.eventFilter(eventFilterDto);
        if (resList.isEmpty()){
            throw new CustomException(3001,"无符合要求的选项");
        }

        return new PageInfo<>(resList);
    }

    @Override
    public void update(EventDto eventDto) {
        if (eventDto.getEventId() == null){
            throw new CustomException(400,"无效请求:赛事id不能为空");
        }

        Event event = new Event();
        BeanUtils.copyProperties(eventDto,event);
        event.setUpdatedAt(LocalDateTime.now());
        if(eventMapper.update(event) == 0){
            throw new CustomException(3003,"重复信息无效更新");
        }
    }

    @Override
    public EventVo getById(Integer id) {
        return eventMapper.getById(id);
    }
}
