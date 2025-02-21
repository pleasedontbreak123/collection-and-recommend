package org.example.collectionandrecommend.demos.web.mapper;

import org.apache.ibatis.annotations.*;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventFilterDto;
import org.example.collectionandrecommend.demos.web.model.entity.Event;
import org.example.collectionandrecommend.demos.web.model.vo.EventVo;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EventMapper {

    @Insert("insert into events (title, description, start_time, end_time, organizer, status, created_at, updated_at) " +
            "values (#{title},#{description},#{startTime},#{endTime},#{organizer},#{status},#{createdAt},#{updatedAt})")
    void add(Event event);

    @Select("SELECT * FROM events")
    @Results({
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "organizer", column = "organizer"),
            @Result(property = "status", column = "status"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Event> findAll();

    void addCateForEvent(@Param("eventId")int eventId,@Param("categoryIds") List<Integer> categoryIds,@Param("localDateTime") LocalDateTime localDateTime);

    List<EventVo> eventFilter(EventFilterDto eventFilterDto);
}
