package org.example.collectionandrecommend;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.collectionandrecommend.demos.web.config.LocalDateTimeAdapter;
import org.example.collectionandrecommend.demos.web.mapper.EventCategoryMapper;
import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventCategoryDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.entity.Event;
import org.example.collectionandrecommend.demos.web.model.vo.EventCategoryVo;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional  // 保证测试完后数据回滚，不会影响数据库
public class TestEventController {




    @Autowired
    private MockMvc mockMvc; // 用于模拟 HTTp

    @Autowired
    private EventService eventService;  // 模拟 EventService

    @Autowired
    private EventMapper eventMapper;  // 用于测试数据库持久化

    @Autowired
    private EventCategoryMapper eventCategoryMapper;

    @Test
    public void testAddEvent() throws Exception {


        // 创建一个 EventDto 对象
        EventDto eventDto = new EventDto();
        eventDto.setEventId(0);
        eventDto.setTitle("Test Event");
        eventDto.setDescription("test");
        eventDto.setOrganizer("test");
        eventDto.setEndTime(LocalDateTime.now());
        eventDto.setStartTime(LocalDateTime.now());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        // 模拟 eventService.add(eventDto) 调用并持久化
        //Mockito.doNothing().when(eventService).add(Mockito.any(EventDto.class));
        String eventJson = gson.toJson(eventDto);



        // 发起 POST 请求并验证结果
        // 发起 POST 请求并验证结果
        mockMvc.perform(post("/event/addEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))  // 使用 Gson 生成的 JSON 字符串
                .andExpect(status().isOk());  // 期望 HTTP 状态码为 200

        // 测试数据库持久化，验证数据是否被保存
        List<Event> events = eventMapper.findAll();
        //assertEquals(1, events.size());  // 期望数据库中保存了一个 event
        Event event = events.get(events.size()-1);
        assertEquals("Test Event", event.getTitle());  // 期望事件名称为 "Test Event"
    }


    @Test
    public void testAddCate() throws Exception{
        EventCategoryDto eventCategoryDto = new EventCategoryDto();
        eventCategoryDto.setCategoryName("新分类");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        String cateJson = gson.toJson(eventCategoryDto);
        mockMvc.perform(post("/category/addCate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cateJson))  // 使用 Gson 生成的 JSON 字符串
                .andExpect(status().isOk());  // 期望 HTTP 状态码为 200)
        List<EventCategoryVo> eventCategoryVos = eventCategoryMapper.findAll();
        EventCategoryVo eventCategoryVo = eventCategoryVos.get(eventCategoryVos.size()-1);
        assertEquals("新分类",eventCategoryVo.getCategoryName());

    }

    @Test
    public void testAddCateForEvent() throws Exception{
        //int eventId = 1;

        mockMvc.perform(post("/event/addCateForEvent")
                        .param("eventId", "2")
                        .param("categoryIds", "2", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("成功"));
    }
}
