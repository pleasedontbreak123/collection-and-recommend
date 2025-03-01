package org.example.collectionandrecommend.demos.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class CategoryServiceImpl implements CategoryService {

    private final EventCategoryMapper eventCategoryMapper;

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
        // 启动分页查询
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<EventCategoryVo> EventCates = eventCategoryMapper.findAll();

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
