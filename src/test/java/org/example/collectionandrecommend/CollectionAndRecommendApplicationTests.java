package org.example.collectionandrecommend;

import org.example.collectionandrecommend.demos.web.mapper.EventCategoryMapper;
import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventFilterDto;
import org.example.collectionandrecommend.demos.web.model.vo.EventVo;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CollectionAndRecommendApplicationTests {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventCategoryMapper eventCategoryMapper;
    @Autowired
    private EventService eventService;


    @Test
    void contextLoads() {
        EventFilterDto eventFilterDto = new EventFilterDto();
        eventFilterDto.setCategoryId(0);
        //eventFilterDto.setKeywords("2");

       List<EventVo> list =  eventMapper.eventFilter(eventFilterDto);
       System.out.println(list);
    }

}
