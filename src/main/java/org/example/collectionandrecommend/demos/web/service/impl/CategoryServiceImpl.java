package org.example.collectionandrecommend.demos.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionException;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.mapper.EventCategoryMapper;
import org.example.collectionandrecommend.demos.web.mapper.EventMapper;
import org.example.collectionandrecommend.demos.web.model.dto.EventCategoryDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.entity.EventCategory;
import org.example.collectionandrecommend.demos.web.model.vo.EventCategoryVo;
import org.example.collectionandrecommend.demos.web.model.vo.EventVo;
import org.example.collectionandrecommend.demos.web.service.CategoryService;
import org.example.collectionandrecommend.demos.web.utils.LocalCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class CategoryServiceImpl implements CategoryService {

    private final EventCategoryMapper eventCategoryMapper;

    // 创建缓存：最多缓存 100 个 key，默认过期时间 10 分钟
    private final LocalCache<String, List<EventCategoryVo>> cache = new LocalCache<>(100, 10 * 60 * 1000L);


    @Override
    public void addCate(EventCategoryDto eventCategoryDto) throws CustomException {
        if (eventCategoryDto.getCategoryName() == null || eventCategoryDto.getCategoryName().isEmpty()){
            throw new CustomException(400,"分类名不能为空");
        }
        EventCategory eventCategory = new EventCategory();
        BeanUtils.copyProperties(eventCategoryDto,eventCategory);
        eventCategory.setStatus(EventCategory.CategoryStatus.ACTIVE);
        eventCategory.setCreateAt(LocalDateTime.now());

        eventCategoryMapper.addCate(eventCategory);

    }

    @Override
    public PageInfo<EventCategoryVo> listAll(int pageNum, int pageSize) throws CustomException {

        String key = "Categories";

        // 1. 从缓存读取
        List<EventCategoryVo> cachedList = cache.get(key);
        if (cachedList != null) {
            log.info("从缓存获取"+cachedList);
            // 手动分页（从缓存切片）
            int total = cachedList.size();
            int startIndex = (pageNum - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, total);

            // 防止分页参数越界
            if (startIndex >= total) {
                return new PageInfo<>(Collections.emptyList());
            }

            List<EventCategoryVo> pageList = cachedList.subList(startIndex, endIndex);

            // 构造分页信息
            PageInfo<EventCategoryVo> pageInfo = new PageInfo<>(pageList);
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(total);
            pageInfo.setPages((total + pageSize - 1) / pageSize);
            return pageInfo;
        }

        // 启动分页查询
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<EventCategoryVo> EventCates = eventCategoryMapper.findAll();
        cache.put(key, EventCates);

        // 使用 PageInfo 来封装分页信息
        PageInfo<EventCategoryVo> pageInfo = new PageInfo<>(EventCates);

        return pageInfo;
    }

    @Override
    public void delete(Integer cateId) throws CustomException{
        if (cateId == null){
            throw new CustomException(400,"分类id不能为空");
        }
        try {
            if(eventCategoryMapper.deleteCate(cateId) == 0) {
                throw new CustomException(409, "分类ID在event_categories表中不存在");
            }
            eventCategoryMapper.deleteRelation(cateId);

        }catch (DataAccessException e){
            throw new CustomException(500, "数据库操作失败：" + e.getMessage());
        }
    }
}
