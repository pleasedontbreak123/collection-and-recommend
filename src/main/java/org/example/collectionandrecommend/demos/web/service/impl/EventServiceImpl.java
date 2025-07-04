package org.example.collectionandrecommend.demos.web.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionException;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventFilterDto;
import org.example.collectionandrecommend.demos.web.model.entity.Event;
import org.example.collectionandrecommend.demos.web.model.vo.EventCategoryVo;
import org.example.collectionandrecommend.demos.web.model.vo.EventVo;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.example.collectionandrecommend.demos.web.utils.LocalCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;

    // 创建缓存：最多缓存 100 个 key，默认过期时间 10 分钟
    private final LocalCache<String, PageInfo<EventVo>> cache = new LocalCache<>(100, 10 * 60 * 1000L);

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
//        String key = "events";
//        if (eventFilterDto.getCategoryId() == null && Objects.equals(eventFilterDto.getKeywords(), "")){
//            List<EventVo> cachedList = cache.get(key);
//            if (cachedList != null) {
//                log.info("从缓存获取 eventlist ------" + cachedList.size());
//               PageInfo<EventVo> pageInfo = LocalCache.paginateFromCache(cachedList,pageNum,pageSize);
//               return pageInfo;
//            }
//            // 启动分页查询
//            PageHelper.startPage(pageNum, pageSize);
//
//            List<EventVo> resList = eventMapper.eventFilter(eventFilterDto);
//            log.info("原始长度########@@@@@@@@!!!!!"+ resList.size());
//            if (resList.isEmpty()){
//                throw new CustomException(3001,"无符合要求的选项");
//            }
//
//            cache.put(key,resList);
//
//            return new PageInfo<>(resList);
//        }
//        // 启动分页查询
//        PageHelper.startPage(pageNum, pageSize);
//
//        List<EventVo> resList;
//
//        resList = eventMapper.eventFilter(eventFilterDto);
//        if (resList.isEmpty()){
//            throw new CustomException(3001,"无符合要求的选项");
//        }
//
//        return new PageInfo<>(resList);

        // 拼接唯一缓存key，包含查询条件和页码，保证不同条件和页码缓存独立
        String key = "events:" +
                (eventFilterDto.getCategoryId() == null ? "null" : eventFilterDto.getCategoryId()) + ":" +
                (eventFilterDto.getKeywords() == null ? "" : eventFilterDto.getKeywords()) + ":" +
                pageNum + ":" + pageSize;

        // 先从缓存拿PageInfo
        PageInfo<EventVo> cachedPage = cache.get(key);
        if (cachedPage != null) {
            log.info("从缓存获取分页数据，key=" + key);
            return cachedPage;
        }

        // 缓存没命中，数据库分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<EventVo> list = eventMapper.eventFilter(eventFilterDto);
        if (list.isEmpty()) {
            throw new CustomException(3001, "无符合要求的选项");
        }
        PageInfo<EventVo> pageInfo = new PageInfo<>(list);

        // 缓存当前页的分页结果
        cache.put(key, pageInfo);

        return pageInfo;
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
