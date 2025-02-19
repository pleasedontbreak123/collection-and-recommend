package org.example.collectionandrecommend.demos.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class EventController {

    private final EventService eventService;

    @LogAnnotation("添加竞赛")
    @PostMapping("/addEvent")
    public Result addEvent(@RequestBody EventDto eventDto){
            eventService.add(eventDto);
            return Result.success("添加成功");

    }

    @LogAnnotation("为竞赛添加分类")
    @PostMapping("/addCateForEvent")
    public Result cateForEvent(@RequestParam int eventId, @RequestParam List<Integer> categoryIds){
        eventService.addCateForEvent(eventId,categoryIds);
        return Result.success("为竞赛添加分类");
    }


}
