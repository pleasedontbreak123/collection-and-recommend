package org.example.collectionandrecommend;

import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
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
    private EventService eventService;


    @Test
    void contextLoads() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        eventMapper.addCateForEvent(1,list,LocalDateTime.now());
    }

}
