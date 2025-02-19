package org.example.collectionandrecommend.demos.web.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.collectionandrecommend.demos.web.exception.CustomException;
import org.example.collectionandrecommend.demos.web.model.dto.EventCategoryDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.vo.EventCategoryVo;

public interface CategoryService {

    void addCate(EventCategoryDto eventCategoryDto) throws CustomException;

    PageInfo<EventCategoryVo> listAll(int pageNum, int pageSize) throws CustomException;
}
